const app = document.querySelector('.weather-app');
const temp = document.querySelector('.temp');
const dateOutput = document.querySelector('.date');
const timeOutput = document.querySelector('.time');
const conditionOutput = document.querySelector('.condition');
const nameOutput = document.querySelector('.name');
const icon = document.querySelector('.icon');
const cloudOutput = document.querySelector('.cloud');
const humidityOutput = document.querySelector('.humidity');
const windOutput = document.querySelector('.wind');
const form = document.getElementById('locationInput');
const search = document.querySelector('.search');
const button = document.querySelector('.submit');
const cities = document.querySelectorAll('.city');

// default city is our city
let cityInput = "Dublin";

// click event to make it change via fetch
cities.forEach((city) => {
    city.addEventListener('click', (e) => {
        cityInput = e.target.innerHTML;

        fetchWeatherData();

        app.style.opacity = "0";
    });
});

form.addEventListener('submit', (e) => {
    if (search.value.length === 0) {
        alert('Please type in a city name');
    } else {
        cityInput = search.value;

        fetchWeatherData();

        saveHistoryData(cityInput);

        search.value = "";

        app.style.opacity = "0";
    }

    e.preventDefault();
});

    //idk how but when I starts the day with monday is takes the data wrongly and thinks its thursday
function dayOfTheWeek(day, month, year) {
    const weekday = [
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday"
    ];
    return weekday[new Date(`${day}/${month}/${year}`).getDay()];
}


function saveHistoryData (text) {
    fetch(`/history-save`, {
        method : "POST",
        body : {text}
        
    }).then(console.log)
}

    // my api link and key
function fetchWeatherData() {
    fetch(`http://api.weatherapi.com/v1/current.json?key=2d3629f7f4634af3b10101637240801&q=${cityInput}`)
    
        .then(response => response.json())
        .then(data => {
            console.log(data);

            temp.innerHTML = data.current.temp_c + "&#176;";
            conditionOutput.innerHTML = data.current.condition.text;

            const date = data.location.localtime;
            const y = parseInt(date.substr(0, 4));
            const m = parseInt(date.substr(5, 2));
            const d = parseInt(date.substr(8, 2));
            const time = date.substr(11);

            dateOutput.innerHTML = `${dayOfTheWeek(d, m, y)} ${d}, ${m} ${y}`;
            timeOutput.innerHTML = time;

            nameOutput.innerHTML = data.location.name;

            const iconId = data.current.condition.icon.substr("//cdn.weatherapi.com/weather/64x64".length);
            icon.src = `./icons/${iconId}`;

            cloudOutput.innerHTML = data.current.cloud + "%";
            humidityOutput.innerHTML = data.current.humidity + "%";
            windOutput.innerHTML = data.current.wind_kph + "km/h";

            let timeOfDay = "day";
            const code = data.current.condition.code;

            if (!data.current.is_day) {
                timeOfDay = "night";
            }

            if (code == 1000) {
                app.style.backgroundImage = `url(./images/${timeOfDay}/clear.jpg)`;
                button.style.background = "#e5ba92";

                if (timeOfDay == "night") {
                    button.style.background = "#181e27";
                }
            } else if (
                code == 1003 ||
                code == 1006 ||
                code == 1009 ||
                code == 1030 ||
                code == 1069 ||
                code == 1087 ||
                code == 1135 ||
                code == 1273 ||
                code == 1276 ||
                code == 1279 ||
                code == 1282
            ) {
                app.style.backgroundImage = `url(./images/${timeOfDay}/cloudy.jpg)`;
                button.style.background = "#fa6d1b";

                if (timeOfDay == "night") {
                    button.style.background = "#181e27";
                }
            } else if (
                code == 1063 ||
                code == 1069 ||
                code == 1072 ||
                code == 1150 ||
                code == 1153 ||
                code == 1180 ||
                code == 1183 ||
                code == 1186 ||
                code == 1189 ||
                code == 1192 ||
                code == 1195 ||
                code == 1204 ||
                code == 1207 ||
                code == 1240 ||
                code == 1243 ||
                code == 1246 ||
                code == 1249 ||
                code == 1252
            ) {
                app.style.backgroundImage = `url(./images/${timeOfDay}/rainy.jpg)`;
                button.style.background = "#647d75";

                if (timeOfDay == "night") {
                    button.style.background = "#325c80";
                }
            } else {
                app.style.backgroundImage = `url(./images/${timeOfDay}/snowy.jpg)`;
                button.style.background = "#4d72aa";

                if (timeOfDay == "night") {
                    button.style.background = "#1b1b1b";
                }
            }

            app.style.opacity = "1";
        })
        //alert for wrong location name
        .catch(() => {
            alert('City not found!');
            app.style.opacity = "1";
        });
}

fetchWeatherData();
app.style.opacity = "1";


function validateLogin(event) {
    event.preventDefault();

    // gett values from the login form
    let loginEmail = document.getElementById('loginemail').value;
    let loginPassword = document.getElementById('loginpassword').value;

    // if email or password fields are empty
    if (loginEmail.trim() === '') {
        alert('Email is missing.');
        return;
    }

    if (loginPassword.trim() === '') {
        alert('Password is missing.');
        return;
    }


    const validEmail = 'user@example.com'; 
    const validPassword = 'password'; 

    if (loginEmail === validEmail && loginPassword === validPassword) {
        alert('Login successful!');
        // Redirect to main.html
        window.location.href = '/main';
    } else {
        alert('Your email or password is incorrect.');
    }
}

