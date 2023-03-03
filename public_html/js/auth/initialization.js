function setCookie(name, value, days) {
    let expires = ""
    if (days) {
        const date = new Date()
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000))
        expires = "; expires=" + date.toUTCString()
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/"
}

function initKeycloak() {
    const keycloak = new Keycloak()
    keycloak.init(
        {
            onLoad: 'check-sso',
            silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html'
        }
    ).then(function (authenticated) {
        if (!authenticated) {
            keycloak.login()
        } else {
            const token = keycloak.token
            setCookie('jwtToken', token, 30)
        }
    }).catch(function (error) {
        console.error(error)
    })
}

document.addEventListener("DOMContentLoaded", function (event) {
    initKeycloak()
})