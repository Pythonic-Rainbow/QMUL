function confirm_reset() {
    return confirm("Are you sure you want to reset all text?");
}

function submittt(event) {
    document.getElementById('form').action = 'addPost.php'

    let title = document.getElementById('title')
    let body = document.getElementById('body')

    title.classList.remove('empty')
    body.classList.remove('empty')


    if (title.value == '') {
        title.classList.add('empty')
        event.preventDefault()
    }
    
    if (body.value == '') {
        body.classList.add('empty')
        event.preventDefault()
    }  
}

function previewww(event) {
    submittt(event)
    document.getElementById('form').action = 'preview.php'
}