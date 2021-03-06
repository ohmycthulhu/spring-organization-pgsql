$('form').submit(function (event) {
    if ($(this).data('ignore')) {
        return;
    }

    console.log('Starting')
    event.preventDefault();

    if ($(this).data('ask') && !confirm('Are you sure?')) {
        return;
    }

    const data = $(this).serializeArray();
    const url = $(this).attr('action');

    const resultData = data.reduce((acc, param) => {
        if (param.name != 'boss[id]') {
            return {
                ...acc,
                [param.name]: param.value
            }
        } else {
            return {
                ...acc,
                boss: param.value ? ({
                    id: param.value
                }) : null
            }
        }
    }, {})
    console.log(resultData);

    const destination = $(this).data('redirect') || '/'

    $.ajax({
      type: "POST",
      url,
      data: JSON.stringify(resultData),
      success (data) {
        if (location.href === destination) {
            location.reload(false);
        } else {
            location.href = '/home';
        }
        console.log('something', data)
      },
      error ({ status }) {
        console.log(status)
        if (status === 409) {
            alert("User is someone's boss")
        }
        if (status === 200) {
            if (location.href === destination) {
                location.reload(false);
            } else {
                location.href = '/';
            }
        }
        console.log('Somehow')
      },
      dataType: "json",
      contentType : "application/json",
    });
})