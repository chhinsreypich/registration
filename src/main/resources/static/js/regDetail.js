  document.querySelectorAll('.remove-btn').forEach(btn => {
    btn.addEventListener('click', function () {
      const regId = this.getAttribute('data-id');

      const formData = new URLSearchParams();
      formData.append("registrationId", regId);

      fetch('/registrations/remove-member', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData
      }).then(resp => {
        if (resp.ok) {
          document.getElementById('reg-' + regId).remove();
        } else {
          alert('Failed to remove member');
        }
      }).catch(() => alert('Error removing member'));
    });
  });