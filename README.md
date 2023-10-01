[](#readme-top)
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#-flavorfusion">The Project</a></li>
    <li><a href="#-architecture">Architecture</a></li>
    <li><a href="#-used-libraries">Used libraries</a></li>
     <li><a href="#-used-libraries">Build Process</a></li>
    <li><a href="#-contact">Contact</a></li>
    <li><a href="#-credits">Credits</a></li>
  </ol>
</details>

# Flavor Fusion
Flavor Fusion is a recipe search application built in Kotlin
<p align="right"><a href="#readme-top">back to top</a></p>

## 🧩 Architecture
MVVM with Clean Architecture and Jetpack components

Modules
* app: 
Contains Android components
  * presentation
  * framework
* core: 
Contains business rules, use cases and repositories
  * use cases
  * repository
domain
* testing: 
Contains shared testing codes used by other modules
<p align="right"><a href="#readme-top">back to top</a></p>

## 📚 Used libraries

Network Management
* Retrofit
* OkHttp

Code Quality
* Deteckt
* Spotless

UI/UX
* Shimmer
* Glide

Data Persistence
* Datastore
* Room

Dependency Management
* Dagger Hilt

Navigation
* Navigation

Pagination
* Paging3

Asynchronous Programming
* Coroutines

Unit Testing
* Mockito

Optimization and Security
* Proguard
<p align="right"><a href="#readme-top">back to top</a></p>

## 🚀 Build Process
To build the project on your machine, follow the steps below:

1. Generate an API Key
Go to the link https://spoonacular.com/food-api/console#Dashboard and sign up.
Log in, on the right-hand side, click on Profile, then click on "Show/Hide API Key," and you will see a sequence of numbers in front of API Key:

2. Add API Key to the project
In the project's root directory, create a file called api.properties.
Inside this file, add the code below, replacing "paste your key here" with your API key generated in the previous step.

<div>
  <button id="copyButton"></button>
  <pre><code id="code">
API_KEY="paste your key here"
  </code></pre>
</div>

<!-- Script to copy the code -->
<!-- 
<script>
document.getElementById('copyButton').addEventListener('click', function () {
  const codeElement = document.getElementById('code');
  const textArea = document.createElement('textarea');
  textArea.value = codeElement.textContent;
  document.body.appendChild(textArea);
  textArea.select();
  document.execCommand('copy');
  document.body.removeChild(textArea);
  alert('Code copied!');
});
</script>
-->

That's it! Now you can build the project.

## 👩‍💻 Contact
[LinkedIn](https://www.linkedin.com/in/mellina-cerqueira/)
<p align="right"><a href="#readme-top">back to top</a></p>

## 🤝 Credits
The data consumed in this app comes from the [Food API](https://spoonacular.com/food-api)
 
App developed based on learning acquired at [CAMP](https://douglasmotta.com.br/curso-android-moderno-profissional/) 
<p align="right"><a href="#readme-top">back to top</a></p>
