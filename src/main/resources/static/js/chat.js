async function sendChatMessage(message) {
    // const token = localStorage.getItem('jwtToken');
    const token = localStorage.getItem('JWT');
    const res = await fetch('/api/chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ message })
    });
    const data = await res.json();
    if (data.status === 200) {
        appendChatBubble('assistant', data.body.reply);
    } else {
        appendChatBubble('assistant', 'Sorry, something went wrong.');
    }
}

function appendChatBubble(sender, text) {
    const messagesDiv = document.getElementById('chatMessages');
    const bubble = document.createElement('div');
    bubble.className = `chat-bubble ${sender}`;
    bubble.textContent = text;
    messagesDiv.appendChild(bubble);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

document.getElementById('chatSendBtn').addEventListener('click', () => {
    const input = document.getElementById('chatInput');
    const message = input.value.trim();
    if (message) {
        appendChatBubble('user', message);
        sendChatMessage(message);
        input.value = '';
    }
});

document.getElementById('chatToggleBtn').addEventListener('click', () => {
    document.getElementById('chatWidget').classList.toggle('hidden');
});

document.getElementById('chatCloseBtn').addEventListener('click', () => {
    document.getElementById('chatWidget').classList.add('hidden');
});