import { API_BASE_URL } from "../app-config";

export function call(api, method, request) {
  let headers = new Headers({
    "Content-Type": "application/json"
  }); 

  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if(accessToken && accessToken !== null) {
    headers.append("Authorization", "Bearer "+accessToken);
  }

  const options = {
    headers: headers,
    url: API_BASE_URL + api,
    method: method
  };

  if(request) {
    options.body = JSON.stringify(request);
  };

  return fetch(options.url, options)
    .then((response) =>
      response.json().then((json) => {
        if(!response.ok) {
          return Promise.reject(json);
        }
        return json;
      })
    )
    .catch((error) => {
      console.log(error)
      if(error.status === 403) {
        window.location.href = "/login"; // redirect
      }
      return Promise.reject(error);
    });

}

export function signin(userDTO) {
  return call("/auth/signin", "POST", userDTO)
    .then((response) => {
      if(response.token) {
        localStorage.setItem("ACCESS_TOKEN", response.token);
        window.location.href = "/";
      }
    })
}

export function signout() {
  localStorage.setItem("ACCESS_TOKEN", null);
  window.location.href = "/login";
}

export function signup() {
  return call("/auth/signup", "POST", userDTO);
}