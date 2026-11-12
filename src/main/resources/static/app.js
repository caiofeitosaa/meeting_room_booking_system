const API = {
    login: '/users/login',
    register: '/users/create',
    searchRoom: (roomNumber) => `/rooms/search?roomNumber=${encodeURIComponent(roomNumber)}`,
    searchReservations: (roomNumber, date) => `/reservation/search?roomNumber=${encodeURIComponent(roomNumber)}&date=${encodeURIComponent(date)}`,
    createReservation: '/reservation/create'
};

const userKey = 'gather.user';
const modal = document.querySelector('#auth-modal');
const bookingModal = document.querySelector('#booking-modal');
const authForm = document.querySelector('#auth-form');
const authMessage = document.querySelector('#auth-message');
const scheduleForm = document.querySelector('#schedule-form');
const bookingForm = document.querySelector('#booking-form');
const results = document.querySelector('#schedule-results');
let authMode = 'login';
let bookingAfterLogin = false;
let toastTimer;

function getUser() {
    try {
        return JSON.parse(localStorage.getItem(userKey) || 'null');
    } catch (error) {
        localStorage.removeItem(userKey);
        return null;
    }
}

function updateUserUI() {
    const user = getUser();
    document.querySelector('#user-name').textContent = user ? user.name : 'Guest';
    document.querySelector('#user-email').textContent = user ? user.email : 'Sign in to get started';
    document.querySelector('#user-avatar').textContent = user?.name?.trim()?.[0]?.toUpperCase() || 'G';
}

function setPage(page) {
    const schedule = page === 'schedule';
    document.querySelector('#home-page').classList.toggle('active', !schedule);
    document.querySelector('#schedule-page').classList.toggle('active', schedule);
    document.querySelectorAll('.nav-link').forEach((link) => {
        const active = link.dataset.page === (schedule ? 'schedule' : 'home');
        link.classList.toggle('active', active);
        link.setAttribute('aria-current', active ? 'page' : 'false');
    });
    document.querySelector('#page-title').textContent = schedule ? 'Room schedule' : 'Overview';
}

function showToast(message) {
    const toast = document.querySelector('#toast');
    toast.textContent = message;
    toast.classList.add('visible');
    clearTimeout(toastTimer);
    toastTimer = setTimeout(() => toast.classList.remove('visible'), 3600);
}

function openAuth(mode = 'login') {
    setAuthMode(mode);
    authMessage.textContent = '';
    modal.hidden = false;
    document.querySelector(mode === 'register' ? '#auth-name' : '#auth-email').focus();
}

function closeAuth() {
    modal.hidden = true;
    authMessage.textContent = '';
    bookingAfterLogin = false;
}

function localDateValue(date) {
    const year = date.getUTCFullYear();
    const month = String(date.getUTCMonth() + 1).padStart(2, '0');
    const day = String(date.getUTCDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

function openBooking() {
    const user = getUser();
    if (!user) {
        bookingAfterLogin = true;
        openAuth('login');
        return;
    }
    const start = new Date();
    start.setUTCMinutes(0, 0, 0);
    start.setUTCHours(start.getUTCHours() + 1);
    if (start.getUTCHours() < 8) start.setUTCHours(8);
    if (start.getUTCHours() > 18) {
        start.setUTCDate(start.getUTCDate() + 1);
        start.setUTCHours(8);
    }
    const end = new Date(start);
    end.setUTCHours(end.getUTCHours() + 1);
    document.querySelector('#booking-date').value = localDateValue(start);
    document.querySelector('#booking-start').value = `${String(start.getUTCHours()).padStart(2, '0')}:00`;
    document.querySelector('#booking-end').value = `${String(end.getUTCHours()).padStart(2, '0')}:00`;
    document.querySelector('#booking-message').textContent = '';
    document.querySelector('#booking-subtitle').textContent = `Booking for ${user.name}.`;
    bookingModal.hidden = false;
    document.querySelector('#booking-room').focus();
}

function closeBooking() {
    bookingModal.hidden = true;
    document.querySelector('#booking-message').textContent = '';
}

function setAuthMode(mode) {
    authMode = mode;
    const registering = mode === 'register';
    document.querySelector('#name-field').hidden = !registering;
    document.querySelector('#auth-name').required = registering;
    document.querySelector('#auth-password').autocomplete = registering ? 'new-password' : 'current-password';
    document.querySelector('#auth-title').textContent = registering ? 'A good place to start.' : 'Welcome back.';
    document.querySelector('#auth-subtitle').textContent = registering ? 'Create your account and bring your team together.' : 'Sign in to book a room with your team.';
    document.querySelector('#auth-submit').innerHTML = `${registering ? 'Create account' : 'Sign in'} <span aria-hidden="true">→</span>`;
    document.querySelector('#login-tab').classList.toggle('active', !registering);
    document.querySelector('#register-tab').classList.toggle('active', registering);
    document.querySelector('#login-tab').setAttribute('aria-selected', String(!registering));
    document.querySelector('#register-tab').setAttribute('aria-selected', String(registering));
    authMessage.textContent = '';
}

function setLoading(form, loading, label) {
    const button = form.querySelector('button[type="submit"]');
    if (!button) return;
    if (loading) {
        button.dataset.originalLabel = button.innerHTML;
        button.disabled = true;
        button.textContent = label;
    } else {
        button.disabled = false;
        if (button.dataset.originalLabel) {
            button.innerHTML = button.dataset.originalLabel;
            delete button.dataset.originalLabel;
        }
    }
}

async function request(url, options = {}) {
    let response;
    try {
        response = await fetch(url, {
            ...options,
            headers: { 'Content-Type': 'application/json', ...options.headers }
        });
    } catch (error) {
        throw new Error('Could not reach the server. Check that the application is running and try again.');
    }

    if (!response.ok) {
        let detail = `Request failed (${response.status}).`;
        try {
            const body = await response.json();
            detail = body.detail || body.message || detail;
        } catch (error) {
            // Keep the HTTP status message when the server does not return JSON.
        }
        throw new Error(detail);
    }

    if (response.status === 204 || response.headers.get('content-length') === '0') return null;
    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

function formatTime(value) {
    return new Intl.DateTimeFormat('en', { hour: 'numeric', minute: '2-digit', timeZone: 'UTC' }).format(new Date(`${value}Z`));
}

function displaySchedule(reservations, room, date) {
    results.replaceChildren();
    document.querySelector('#results-title').textContent = `Room ${room.roomNumber}`;
    document.querySelector('#results-date').textContent = new Intl.DateTimeFormat('en', {
        weekday: 'short', month: 'short', day: 'numeric', timeZone: 'UTC'
    }).format(new Date(`${date}T12:00:00Z`));

    const summary = document.createElement('div');
    summary.className = 'room-summary';
    const capacity = document.createElement('span');
    capacity.innerHTML = '<small>CAPACITY</small>';
    const capacityValue = document.createElement('strong');
    capacityValue.textContent = `${room.capacity} people`;
    capacity.append(capacityValue);
    const projector = document.createElement('span');
    projector.innerHTML = '<small>PROJECTOR</small>';
    const projectorValue = document.createElement('strong');
    projectorValue.textContent = room.hasProjector ? 'Available' : 'Not available';
    projector.append(projectorValue);
    summary.append(capacity, projector);
    results.append(summary);

    if (!Array.isArray(reservations) || reservations.length === 0) {
        const empty = document.createElement('div');
        empty.className = 'empty-state';
        empty.innerHTML = '<span class="empty-icon">✓</span><strong>No meetings on the books</strong><span>This room has no reservations for the selected date.</span>';
        results.append(empty);
        return;
    }

    reservations
        .sort((a, b) => a.startTime.localeCompare(b.startTime))
        .forEach((meeting) => {
            const row = document.createElement('article');
            row.className = 'meeting-row';
            const time = document.createElement('span');
            time.className = 'meeting-time';
            time.textContent = `${formatTime(meeting.startTime)} – ${formatTime(meeting.endTime)}`;
            const details = document.createElement('span');
            details.className = 'meeting-details';
            const title = document.createElement('strong');
            title.textContent = meeting.userName ? `${meeting.userName}’s meeting` : 'Reserved meeting';
            const subtitle = document.createElement('span');
            subtitle.textContent = `Room ${meeting.roomNumber}`;
            details.append(title, subtitle);
            const badge = document.createElement('span');
            badge.className = 'meeting-pill';
            badge.textContent = 'Booked';
            row.append(time, details, badge);
            results.append(row);
        });
}

function showResultMessage(message, isError = false) {
    results.replaceChildren();
    const item = document.createElement('div');
    item.className = `results-message${isError ? ' error' : ''}`;
    item.textContent = message;
    results.append(item);
}

async function loadSchedule(roomNumber, date) {
    setPage('schedule');
    document.querySelector('#schedule-room').value = roomNumber;
    document.querySelector('#schedule-date').value = date;
    showResultMessage('Looking up this room…');
    try {
        const room = await request(API.searchRoom(roomNumber));
        if (!room || room.roomNumber === undefined) throw new Error('This room could not be found.');
        try {
            const reservations = await request(API.searchReservations(roomNumber, date));
            displaySchedule(reservations, room, date);
        } catch (error) {
            if (error.message.includes('No reservations found')) {
                displaySchedule([], room, date);
                return;
            }
            throw error;
        }
    } catch (error) {
        showResultMessage(error.message, true);
    }
}

document.querySelector('#list-rooms').addEventListener('click', async () => {
    const list = document.querySelector('#room-list');
    const message = document.querySelector('#booking-message');
    try {
        const rooms = await request('/rooms');
        list.replaceChildren();
        if (!rooms.length) message.textContent = 'No rooms registered yet.';
        rooms.sort((a, b) => a.roomNumber - b.roomNumber).forEach((room) => {
            const row = document.createElement('button');
            row.type = 'button';
            row.className = 'meeting-row';
            const info = document.createElement('span');
            info.textContent = `Room ${room.roomNumber} · ${room.capacity} people${room.hasProjector ? ' · Projector' : ''}`;
            const pill = document.createElement('span');
            pill.className = 'meeting-pill';
            pill.textContent = 'Select';
            row.append(info, pill);
            row.addEventListener('click', () => {
                document.querySelector('#booking-room').value = room.roomNumber;
                list.querySelectorAll('.selected').forEach((el) => el.classList.remove('selected'));
                row.classList.add('selected');
            });
            list.append(row);
        });
        list.hidden = false;
    } catch (error) {
        message.textContent = error.message;
    }
});

function startBooking() {
    openBooking();
}

document.querySelectorAll('[data-page]').forEach((button) => {
    button.addEventListener('click', () => setPage(button.dataset.page));
});

document.querySelectorAll('[data-action="book"]').forEach((button) => button.addEventListener('click', startBooking));
document.querySelectorAll('[data-action="find-room"]').forEach((button) => button.addEventListener('click', () => {
    setPage('schedule');
    document.querySelector('#schedule-room').focus();
}));

document.querySelector('#profile-button').addEventListener('click', () => {
    if (getUser()) {
        localStorage.removeItem(userKey);
        updateUserUI();
        showToast('You have signed out.');
    } else {
        openAuth('login');
    }
});

document.querySelector('#login-tab').addEventListener('click', () => setAuthMode('login'));
document.querySelector('#register-tab').addEventListener('click', () => setAuthMode('register'));
document.querySelectorAll('[data-action="close-modal"]').forEach((button) => button.addEventListener('click', closeAuth));
document.querySelectorAll('[data-action="close-booking"]').forEach((button) => button.addEventListener('click', closeBooking));
modal.addEventListener('click', (event) => {
    if (event.target === modal) closeAuth();
});
bookingModal.addEventListener('click', (event) => {
    if (event.target === bookingModal) closeBooking();
});
document.addEventListener('keydown', (event) => {
    if (event.key === 'Escape' && !modal.hidden) closeAuth();
    if (event.key === 'Escape' && !bookingModal.hidden) closeBooking();
});

authForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    authMessage.textContent = '';
    const formData = new FormData(authForm);
    const payload = {
        email: formData.get('email').trim(),
        password: formData.get('password')
    };
    if (authMode === 'register') payload.name = formData.get('name').trim();

    setLoading(authForm, true, authMode === 'register' ? 'Creating account…' : 'Signing in…');
    try {
        if (authMode === 'register') {
            await request(API.register, { method: 'POST', body: JSON.stringify(payload) });
            setAuthMode('login');
            document.querySelector('#auth-email').value = payload.email;
            authMessage.textContent = 'Account created. Sign in to continue.';
        } else {
            const user = await request(API.login, { method: 'POST', body: JSON.stringify(payload) });
            if (!user || user.id == null || !user.name || !user.email) {
                throw new Error('The server returned an incomplete user profile.');
            }
            localStorage.setItem(userKey, JSON.stringify({ id: user.id, name: user.name, email: user.email }));
            updateUserUI();
            const continueToBooking = bookingAfterLogin;
            bookingAfterLogin = false;
            closeAuth();
            if (continueToBooking) {
                openBooking();
            } else {
                showToast(`Welcome, ${user.name}!`);
            }
        }
    } catch (error) {
        authMessage.textContent = error.message;
    } finally {
        setLoading(authForm, false);
    }
});

scheduleForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const formData = new FormData(scheduleForm);
    const roomNumber = formData.get('roomNumber');
    const date = formData.get('date');
    if (!roomNumber || !date) return;
    setLoading(scheduleForm, true, 'Searching…');
    await loadSchedule(roomNumber, date);
    setLoading(scheduleForm, false);
});

bookingForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const user = getUser();
    if (!user) {
        closeBooking();
        openAuth('login');
        bookingAfterLogin = true;
        return;
    }
    const formData = new FormData(bookingForm);
    const roomNumber = Number(formData.get('roomNumber'));
    const date = formData.get('date');
    const startTime = formData.get('startTime');
    const endTime = formData.get('endTime');
    const start = new Date(`${date}T${startTime}:00Z`);
    const end = new Date(`${date}T${endTime}:00Z`);
    const message = document.querySelector('#booking-message');
    const opening = new Date(`${date}T08:00:00Z`);
    const closing = new Date(`${date}T19:00:00Z`);
    if (start < new Date() || start < opening || end > closing || end <= start || end - start < 3600000) {
        message.textContent = 'Choose a future time between 8:00 AM and 7:00 PM. Meetings must last at least one hour.';
        return;
    }
    message.textContent = '';
    setLoading(bookingForm, true, 'Booking your room…');
    try {
        await request(API.createReservation, {
            method: 'POST',
            body: JSON.stringify({
                roomNumber,
                userId: user.id,
                userName: user.name,
                startDateTime: `${date}T${startTime}:00`,
                endDateTime: `${date}T${endTime}:00`
            })
        });
        closeBooking();
        showToast(`Room ${roomNumber} is booked. See you there!`);
        await loadSchedule(roomNumber, date);
    } catch (error) {
        message.textContent = error.message;
    } finally {
        setLoading(bookingForm, false);
    }
});

document.querySelector('#today-label').textContent = new Intl.DateTimeFormat('en', {
    weekday: 'short', month: 'short', day: 'numeric', timeZone: 'UTC'
}).format(new Date()) + ' · UTC';
document.querySelector('#schedule-date').value = localDateValue(new Date());
updateUserUI();
