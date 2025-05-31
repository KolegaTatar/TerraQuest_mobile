<div style="display: flex; align-items: center; gap: 10px;">
  <img src="app/res/drawable/tq_logo.png" alt="Logo" width="100">
  <h1 style="color:gold; margin: 0;">TerraQuest</h1>
</div>

**TerraQuest_mobile** to natywna aplikacja na Androida będąca częścią ekosystemu TerraQuest – platformy wspomagającej planowanie podróży, zarządzanie atrakcjami turystycznymi oraz organizowanie wycieczek w Polsce i za granicą. Projekt ten powstał jako część pracy inżynierskiej studentów Zespołu Szkół Komunikacji w Poznaniu.

Aplikacja mobilna służy jako klient dla użytkowników, którzy chcą szybko i wygodnie odkrywać atrakcje turystyczne oraz tworzyć własne trasy podróży na smartfonie.

---

## 📲 Demo aplikacji

<img src="TerraQuest/app/src/main/res/drawable/screen1.jpg" alt="Screen aplikacji 1" width="100"><img src="TerraQuest/app/src/main/res/drawable/screen2.jpg" alt="Screen aplikacji 2" width="100">

---

## 🧭 Główne funkcje

### 🔍 Odkrywanie atrakcji
- Przeglądanie listy atrakcji turystycznych.
- Filtrowanie wg kategorii (np. zamek, muzeum, góry).
- Widok szczegółowy atrakcji: opis, lokalizacja, zdjęcie, godziny otwarcia.

### 📌 Planowanie trasy
- Dodawanie atrakcji do planu podróży.
- Tworzenie i zarządzanie trasami.
- Podgląd trasy na mapie (Google Maps – planowane).

### 💬 Centrum pomocy (FAQ)
- System FAQ z wyszukiwarką i paginacją.
- Rozwijane odpowiedzi z animacjami.
- Intuicyjny i przejrzysty interfejs użytkownika.

### 🗂️ Profil użytkownika (planowane)
- Rejestracja i logowanie.
- Zapisywanie tras w chmurze.
- Historia wycieczek.

---

## 💡 Technologie

| Warstwa        | Technologie                         |
|----------------|-------------------------------------|
| Android UI     | XML Layouts, Fragments, RecyclerView|
| Backend lokalny| SQLite (AppDatabaseHelper)          |
| Język          | Java (min SDK 24)                   |
| Architektura   | MVP / częściowo MVVM                |
| IDE            | Android Studio                      |

---

# ⚙️ Instalacja
**📥 Krok 1 – Klonowanie repozytorium**
   ```bash
      git clone https://github.com/KolegaTatar/TerraQuest_mobile.git
   ```
**⬇️ Krok 2 – Przejście do projektu**
   ```bash
      cd TerraQuest_mobile/TerraQuest
   ```

# Podział pracy w projekcie TerraQuest

## Frontend:



---

## Backend:



---

## Baza danych:

- **AppDatabaseHelper.java**: [Każdy]
- **DatabaseHelper.java**: [Każdy]
- **UserDatabaseHelper.java**: [Każdy]

---

## Dokumentacja

- **Przygotowanie dokumentacji technicznej**: [Każdy]

---

# 🌐 Opis podstron / backendu

## Frontend:



---

## Backend:

### `AppDatabaseHelper.java`
- **Opis**:  
  Klasa pomocnicza do zarządzania lokalną bazą danych SQLite w aplikacji TerraQuest. Odpowiada za tworzenie i aktualizację struktury bazy danych oraz inicjalizację danych startowych (recenzje i artykuły pomocy).

- **Funkcje**:
    - Tworzenie tabel:
        - `users` – dane użytkowników (email, hasło, imię, nazwisko, newsletter)
        - `reviews_terraQuest` – recenzje obiektów
        - `help` – sekcja pomocy z pytaniami i odpowiedziami
        - `reservation` – informacje o rezerwacjach z kluczem obcym do użytkownika
        - `date` – dodatkowe dane z oznaczeniem typu i wartości
    - Wstawianie początkowych danych do tabel `reviews_terraQuest` i `help`
    - Obsługa aktualizacji wersji bazy (`onUpgrade`)
    - Zapewnienie spójności danych (np. unikalność adresów e-mail)

### `DatabaseHelper.java`
- **Opis**:  
  Prosta klasa pośrednicząca do obsługi lokalnej bazy danych w aplikacji TerraQuest. Ułatwia dostęp do metod odczytu i zapisu, wykorzystując `AppDatabaseHelper`.

- **Funkcje**:
    - Inicjalizacja pomocnika bazy danych (`AppDatabaseHelper`)
    - Udostępnienie metod:
        - `getReadableDatabase()` – uzyskanie instancji bazy danych tylko do odczytu
        - `getWritableDatabase()` – uzyskanie instancji bazy danych z możliwością zapisu

### `UserDatabaseHelper.java`
- **Opis**:  
  Klasa pomocnicza odpowiedzialna za dostęp do bazy danych użytkowników w aplikacji TerraQuest. Działa jako pośrednik, wykorzystując `AppDatabaseHelper`.

- **Funkcje**:
    - Inicjalizacja obiektu `AppDatabaseHelper`
    - Udostępnienie metod:
        - `getReadableDatabase()` – otwiera bazę danych w trybie tylko do odczytu
        - `getWritableDatabase()` – otwiera bazę danych w trybie do zapisu


---

## Baza danych:



---

## Makieta projektu - Figma:
Opis:
Makieta projektu w Figma to wizualne odwzorowanie interfejsu użytkownika aplikacji lub strony internetowej. Dokumentacja ta przedstawia sposób korzystania z makiety w Figma, jej cele, funkcje oraz strukturę, jak również zapewnia informacje dotyczące współpracy zespołowej i organizacji projektu w Figma.

https://www.figma.com/design/VAEeMmg1rGRkZhTuEwuFnK/Platforma-do-Planowania-Podróży-i-Rezerwacji---TerraQuest?node-id=0-1&t=TFYAgNNcLLNpHKD8-1
---