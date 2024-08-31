# Aplikacja wieloplatformowa do przechowywania przepisów
<p align="center">
  <img src="https://github.com/user-attachments/assets/a46cda38-a4a2-44be-a4e1-6f50249f90d0" alt="app-icon-256"/>
</p>

## Wygląd
<div style="text-align: center;">
  <div style="display: inline-block; margin: 10px;">
    <img src="https://github.com/user-attachments/assets/5d219913-2825-4d47-a9a1-d6d7b013d213" alt="homepage" height="500"/>
  </div>
  <div style="display: inline-block; margin: 10px;">
    <img src="https://github.com/user-attachments/assets/f28c2a32-b2f5-47da-a9a9-4a71bae5eb0c" alt="favoritepage" height="500"/>
  </div>
  <div style="display: inline-block; margin: 10px;">
    <img src="https://github.com/user-attachments/assets/9f2f76aa-3b21-4563-b0ef-72b20aa3a57b" alt="assistantpage" height="500"/>
  </div>
  <div style="display: inline-block; margin: 10px;">
    <img src="https://github.com/user-attachments/assets/dfdc3b51-3763-4b58-bb15-fe713c6d1fbe" alt="detail1" height="500"/>
  </div>
</div>

<div style="text-align: center;">
  <div style="display: inline-block; margin: 10px;">
    <img src="https://github.com/user-attachments/assets/56ec9940-49b6-4e4c-bf9b-4ac9a347116e" alt="detail2" height="500"/>
  </div>
  <div style="display: inline-block; margin: 10px;">
    <img src="https://github.com/user-attachments/assets/b38b8a5e-af67-4a8b-9cf7-760632289d89" alt="edit" height="500"/>
  </div>
  <div style="display: inline-block; margin: 10px;">
    <img src="https://github.com/user-attachments/assets/54c29b46-2a4f-4b66-a8be-690abb8a115b" alt="add" height="500"/>
  </div>
</div>

## Opis
Aplikacja wieloplatformowa umożliwiająca przechowywanie i zarządzanie przepisami kulinarnymi. Dzięki niej możesz tworzyć, edytować i organizować swoje przepisy w prosty i wygodny sposób.

## Możliwości Aplikacji
- **Dodawanie Przepisów:** Każdy przepis może zawierać zdjęcie, tytuł, opis, instrukcje przygotowania, składniki oraz informacje o wartościach odżywczych. Wszystkie przepisy są przechowywane w lokalnej bazie danych.
- **Edycja Przepisów:** Możliwość modyfikowania istniejących przepisów.
- **Dodawanie Przepisów do Ulubionych:** Szybki dostęp do ulubionych przepisów.
- **Podgląd Ostatnio Dodanych Przepisów:** Łatwy dostęp do najnowszych dodanych przepisów.
- **Asystent Sztucznej Inteligencji:** Wbudowany asystent AI, który pomoże w przygotowaniu przepisów.

## Wymagania
*DISCLAIMER*: Aby funkcjonalność czatu ze sztuczną inteligencją działała poprawnie, konieczne jest ręczne dodanie klucza API. Klucz API można wygenerować na stronie, np. [https://console.groq.com/keys](https://console.groq.com/keys).

Po wygenerowaniu klucza, należy go dodać do pliku:
`composeApp/src/commonMain/kotlin/org/example/myrecipes/ai/ChatAIClient.kt`
- w miejscu zmiennej `apiKey` zamiast YOUR_API_KEY.
- istnieje również możliwość zmiany modelu językowego na inny dostępny(występują ograniczenia) [https://console.groq.com/docs/models](https://console.groq.com/docs/models) poprzez zmianę wartości `model` w tym samym pliku.

## Autorzy
- [Mateusz Kostrzewski](https://github.com/Kosusz9)
