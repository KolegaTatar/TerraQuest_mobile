<div style="display: flex; align-items: center; gap: 10px;">
  <img src="TerraQuest/app/src/main/res/drawable/tq_logo.png" alt="Logo" width="100"><h1 style="color:gold; margin: 0;">TerraQuest_mobile</h1>
</div>

**TerraQuest_mobile** to natywna aplikacja na Androida będąca częścią ekosystemu TerraQuest – platformy wspomagającej planowanie podróży, zarządzanie atrakcjami turystycznymi oraz organizowanie wycieczek w Polsce i za granicą. Projekt ten powstał jako część pracy uczniów Zespołu Szkół Komunikacji w Poznaniu.

Aplikacja mobilna służy jako klient dla użytkowników, którzy chcą szybko i wygodnie odkrywać atrakcje turystyczne oraz tworzyć własne trasy podróży na smartfonie.

---

## 📲 Demo aplikacji

<img src="TerraQuest/app/src/main/res/drawable/screen1.jpg" alt="Screen aplikacji 1" width="100"><img src="TerraQuest/app/src/main/res/drawable/screen2.jpg" alt="Screen aplikacji 2" height="200">

---

## 🧭 Główne funkcje

### 🔍 Odkrywanie atrakcji
- Przeglądanie listy atrakcji turystycznych.
- Filtrowanie wg ceny.
- Widok szczegółowy atrakcji: opis, lokalizacja, zdjęcie, cena.

### 💬 Centrum pomocy (FAQ)
- System FAQ z wyszukiwarką i paginacją.
- Rozwijane odpowiedzi z animacjami.
- Intuicyjny i przejrzysty interfejs użytkownika.

### 🗂️ Profil użytkownika
- Rejestracja i logowanie.
- Historia wycieczek.

---

## 💡 Technologie

| Warstwa        | Technologie                         |
|----------------|-------------------------------------|
| Android UI     | XML Layouts, Fragments, RecyclerView|
| Backend lokalny| SQLite (AppDatabaseHelper)          |
| Język          | Java                                |
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

# Podział pracy w projekcie TerraQuest_mobile

## Frontend:

- **activity_main.xml** [Każdy]
- **booking_item.xml** [Jacek]
- **faq_item.xml** [Jacek]
- **fragment_about.xml** [Filip]
- **fragment_contact.xml** [Filip]
- **fragment_explore.xml** [Wiktor]
- **fragment_help.xml** [Jacek]
- **fragment_home.xml** [Wiktor]
- **fragment_login.xml** [Filip]
- **fragment_pp.xml** [Filip]
- **fragment_product.xml** [Wiktor]
- **fragment_reg.xml** [Filip]
- **fragment_search.xml** [Wiktor]
- **fragment_user.xml** [Jacek]
- **fragment_user_menu.xml** [Wiktor]
- **fragment_weather.xml** [Filip]
- **hotel_item.xml** [Wiktor]
- **hotel_item2.xml** [Wiktor]
- **item_timeline_entry.xml** [Filip]
- **recenzja_item.xml** [Wiktor]
- **review_item.xml** [Wiktor]

---

## Backend:

- **HelpAdapter** [Jacek]
- **HelpItem** [Jacek]
- **Hotel** [Wiktor]
- **HotelAdapter** [Wiktor]
- **HotelApiService** [Wiktor]
- **Review** [Wiktor]
- **ReviewPagerAdapter** [Wiktor]
- **AboutFragment** [Filip]
- **ContactFragment** [Filip]
- **ExploreFragment** [Wiktor]
- **HelpFragment** [Jacek]
- **HomeFragment** [Wiktor]
- **LoginFragment** [Filip]
- **PPFragment** [Filip]
- **ProductFragment** [Wiktor/Filip]
- **RegisterFragment** [Filip]
- **SearchFragment** [Wiktor]
- **User_menuFragment** [Wiktor]
- **UserFragment** [Filip]
- **WeatherFragment** [Filip]
- **MainActivity** [Każdy]
- **TrapezoidImageView** [Filip]

---

## Baza danych:

- **AppDatabaseHelper.java**: [Każdy]
- **DatabaseHelper.java**: [Każdy]
- **UserDatabaseHelper.java**: [Każdy]

---

## Diagram bazy danych:

<img src="Database_diagram" alt="Diagram" width="100">

---

## Inne pliki

- **Styles, drawable, layout itd.**: [Każdy]

## Dokumentacja

- **Przygotowanie dokumentacji technicznej**: [Każdy]

---

# 🌐 Opis layoutów / backendu

## Frontend:

# `activity_main.xml`
- **Opis**:  
  Główny layout aplikacji TerraQuest definiujący strukturę interfejsu użytkownika. Zawiera kontener na fragmenty oraz dolną nawigację.

- **Funkcje**:
    - `FrameLayout` o ID `fragment_container` służy do dynamicznego wyświetlania fragmentów aplikacji.
    - `BottomNavigationView` zapewnia dolne menu nawigacyjne z ikonami i etykietami.
    - Spójny styl i kolorystyka zgodna z motywem aplikacji.

---

# `booking_item.xml`

## Opis
Layout reprezentujący pojedynczy element rezerwacji w aplikacji. Przeznaczony do prezentacji podstawowych informacji o rezerwacji z możliwością rozwinięcia szczegółów. Stylizowany z użyciem zaokrąglonego tła i wewnętrznych marginesów dla estetyki i czytelności.

## Funkcje
- **Kontener główny**:  
  `RelativeLayout` z tłem `@drawable/rounded_edittext` i marginesami po bokach oraz od dołu.

- **Strzałka rozwijająca (`@+id/arrow_icon`)**:  
  `ImageView` umieszczony po prawej stronie, służący do rozwijania/zwijania szczegółów. Zawiera ikonę `@drawable/ic_arrow_down` i może być animowany poprzez rotację.

- **Kontener tekstu (`@+id/text_container`)**:  
  `LinearLayout` w układzie pionowym zawierający:

    - `TextView` `booking_title`:  
      Wyświetla tytuł elementu, np. nazwę hotelu. Pogrubiona czcionka, rozmiar 16sp.

    - `TextView` `booking_subtitle`:  
      Wyświetla podtytuł, np. adres. Rozmiar 14sp, kolor #666666.

    - **Sekcja cen** (`LinearLayout` poziomy):
        - `TextView` `booking_old_price`:  
          Poprzednia cena, kolor czerwony (#FF0000), odstęp od nowej ceny.
        - `TextView` `booking_new_price`:  
          Aktualna cena, pogrubiona.

    - `TextView` `booking_details`:  
      Szczegółowy opis rezerwacji, domyślnie ukryty (`visibility="gone"`), wyświetlany po rozwinięciu.

## Styl i UX
- Zaokrąglone rogi i padding zapewniają spójny, nowoczesny wygląd.
- Komponent gotowy do obsługi interakcji użytkownika (np. rozwijanie po kliknięciu).
- Może być używany w listach lub jako część większego widoku rezerwacji.

---

# `faq_item.xml`

## Opis
Layout pojedynczego elementu FAQ z tytułem i rozwijaną odpowiedzią. Stylizowany z zaokrąglonym tłem i ikoną strzałki do interakcji.

## Funkcje
- **Nagłówek (`RelativeLayout`)**:  
  Zawiera `faqTitle` (tekst pytania) oraz `arrowIcon` do rozwijania/zwijania treści.

- **Treść FAQ (`faqContent`)**:  
  Tekst odpowiedzi, domyślnie ukryty (`visibility="gone"`), pojawia się po rozwinięciu.

- **Stylizacja**:  
  Białe tło, padding wewnętrzny, spójny wygląd zgodny z resztą aplikacji.

---

# `fragment_about.xml`

## Opis
Widok przewijalny prezentujący sekcję „O nas” w aplikacji TerraQuest. Zawiera nagłówki, opisy, statystyki oraz oś czasu rozwoju aplikacji.

## Funkcje
- **Obraz nagłówkowy** (`headImage`) — ilustracja w górnej części widoku.
- **Sekcja misji** — żółte tło z tytułem i opisem misji firmy.
- **Opis aplikacji** — nagłówek „O TerraQuest” oraz charakterystyka aplikacji.
- **Statystyki** — cztery tekstowe pola (`textStatsLanguages`, `Apps`, `Countries`, `Hotels`) oddzielone liniami.
- **Oś czasu** (`timelineLayout`) — lista kroków historii aplikacji z użyciem layoutów `item_timeline_entry`.

## Stylizacja
Jasne kolory, przejrzysty układ, przewijalność dzięki `ScrollView`. Wyróżnione sekcje kolorem i paddingiem.

---

# `fragment_contact.xml`

## Opis
Ekran kontaktowy aplikacji TerraQuest z formularzem zapytania oraz danymi kontaktowymi.

## Funkcje
- **Obraz w nagłówku** (`TrapezoidImageView`) – dekoracyjna grafika kontaktowa.
- **Formularz kontaktowy** – pola: Imię, Nazwisko, Email, Wiadomość + przycisk `Wyślij`.
- **Dane kontaktowe**:
    - ☎ Telefon: `+48 517 086 440`
    - 📍 Adres: `Warszawa, ul. Powstańców 21A`
    - ✉ Email: `kontakt@terraquest.pl`

## Stylizacja
Zaokrąglone tła, padding, cień (`elevation`) oraz kolorystyka zgodna z brandingiem (pomarańcz i biel).

# `fragment_explore.xml`

## Opis
Ekran główny aplikacji TerraQuest – zawiera wyszukiwarkę hoteli, listę popularnych miejsc, formularz newslettera oraz oceny użytkowników.

## Sekcje i Funkcje

### 🎯 **Nagłówek promocyjny**
- Informacja o oszczędnościach (`TextView` z tytułem i podtytułem).

### 🔍 **Formularz wyszukiwania hoteli**
- **Miejsce podróży** (`input_destination`)
- **Data** (`editTextDate`)
- **Liczba osób** (`text_people`)
- Przycisk `Szukaj` (`button_search`)

### 🏨 **Popularne wyszukiwania**
- Tytuł + opis
- `RecyclerView` (`recyclerViewHotels`) – lista popularnych lokalizacji/hoteli

### 📨 **Newsletter**
- Sekcja z banerem i formularzem zapisu (`editTextEmail` + `buttonSubscribe`)

### 🌟 **Oceny klientów**
- Teksty promocyjne + `ViewPager2` (`reviewsViewPager`) z opiniami użytkowników

## Stylizacja
- Zaokrąglone przyciski i pola tekstowe (`@drawable/rounded_button`, `rounded_edittext`)
- Odcienie szarości (#757575), czerni i żółci dla kontrastu
- Marginesy wewnętrzne 24dp dla spójności

---

# `fragment_help.xml`

## Opis
Ekran pomocy użytkownika z możliwością przeszukiwania najczęściej zadawanych pytań (FAQ). Zawiera pole wyszukiwania, dynamicznie ładowane odpowiedzi i prostą paginację.

## Sekcje i Funkcje

### 👋 **Nagłówek powitalny**
- `TextView` (`helpTitle`) – zachęcający komunikat: _"Cześć, jak możemy Ci pomóc?"_

### 🔎 **Wyszukiwanie FAQ**
- `EditText` (`searchInput`) – pole tekstowe z podpowiedzią: _"Wyszukaj pytanie"_
- *(Opcjonalny przycisk `searchButton` – zakomentowany, można przywrócić jeśli potrzebny)*

### 📋 **Lista FAQ**
- `ScrollView` zawierający `LinearLayout` (`faqContainer`) – kontener na dynamicznie dodawane pytania/odpowiedzi

### 🔄 **Paginacja**
- `LinearLayout` (`paginationContainer`) z dwoma przyciskami:
    - `prevButton` – przejście do poprzedniej strony
    - `nextButton` – przejście do kolejnej strony

## Styl i UI
- Minimalistyczny, czytelny układ z dużymi marginesami bocznymi (`24dp`)
- Użycie niestandardowego tła i zaokrąglonych elementów (`@drawable/rounded_edittext`)
- Przejrzysty podział na nagłówek, wyszukiwarkę, wyniki i nawigację

---

# `fragment_home.xml`

## 📱 Opis
Ekran startowy aplikacji TerraQuest, umożliwiający użytkownikowi wprowadzenie danych podróży: miejsca docelowego, daty oraz liczby osób. Zawiera także sekcję promującą oszczędności i porównywanie ofert.

## 📋 Zawartość widoku

### 🔶 Nagłówek i opis
- **`TextView`: TerraQuest**  
  Nazwa aplikacji, stylizowana na pomarańczowo (`@color/orange`) z dużym rozmiarem (`35sp`)
- **Opis promocyjny**  
  Informacje o potencjalnych oszczędnościach i funkcji porównywania ofert

### 📥 Formularz wyszukiwania (w `card_background`)
- **`Docelowe miejsce podróży`**
    - `EditText` (`input_destination`) z podpowiedzią (hint): _"Rzym, Włochy"_
- **Separator (`View`)**
    - Cienka linia oddzielająca pola formularza
- **`Data`** (`editTextDate`) – zablokowane pole, domyślnie nieedytowalne, do otwierania np. `DatePickerDialog`
- **`Osoby`** (`text_people`) – pole liczby uczestników

## 🎨 Styl i układ
- Użycie `ScrollView` zapewnia przewijalność na mniejszych ekranach
- Wszystkie główne pola i opisy mają boczne marginesy `24dp` dla spójnego layoutu
- `LinearLayout` z `weightSum="2"` umożliwia estetyczne ułożenie pól **Data** i **Osoby** obok siebie
- Pola `EditText` mają ustandaryzowaną wysokość `48dp` i padding `12dp`, zapewniając wygodę dotykową
- Formularz otoczony kartą (`@drawable/card_background`) z cieniem (`elevation="4dp"`)

---

# `fragment_login.xml`

## 📱 Opis
Ekran logowania użytkownika do aplikacji TerraQuest, z prostym i estetycznym układem zawierającym logo, pola do wpisania e-maila i hasła oraz przyciskiem logowania. Na dole znajduje się link do rejestracji oraz separator "lub".

## 📋 Struktura widoku

### Nagłówek z logo
- **`ImageView`** (`logoImage`) – logo aplikacji (`@drawable/tq_logo`), wymiar 120x120dp
- **`TextView`** (`logoText`) – nazwa aplikacji "TerraQuest", kolor pomarańczowy (`@color/orange`), duża czcionka (`35sp`), pogrubiona

### Formularz logowania (`LinearLayout` z tłem `@drawable/rounded_input`)
- **Tytuł** – `TextView` z napisem "Zaloguj się", wyśrodkowany, rozmiar 28sp
- **E-mail**
    - `TextView` z etykietą "Email"
    - `EditText` (`emailInput`) do wpisania adresu e-mail, podpowiedź "Podaj email", typ `textEmailAddress`
- **Hasło**
    - `TextView` z etykietą "Hasło"
    - `EditText` (`passwordInput`) do wpisania hasła, podpowiedź "Podaj hasło", typ `textPassword`
- **Przycisk logowania**
    - `Button` (`loginButton`) z napisem "Zaloguj się", styl `@style/Log_reg_btn`, ciemny kolor tła (#333333)

### Separator „lub”
- Dwa cienkie paski po bokach `View` (1dp wysokości), szary kolor (#888888)
- W środku tekst "lub" w tym samym odcieniu szarości

### Link do rejestracji
- `TextView` (`registerLink`) z napisem "Zarejestruj się", pogrubiony, czarny tekst, rozmiar 14sp

## 🎨 Styl i układ
- Całość zawinięta w `ScrollView`, aby obsłużyć przewijanie na mniejszych ekranach
- Elementy centrowane horyzontalnie (`gravity="center_horizontal"`)
- Spójne marginesy boczne (`24dp`) i odstępy między elementami
- Zaokrąglone tło formularza (`@drawable/rounded_input`) poprawia estetykę i komfort użytkowania
- Przycisk logowania ma wyraźne tło i styl zdefiniowany w pliku stylów

---

# `fragment_pp.xml`

## 📄 Opis
Ekran z pełnym tekstem Polityki Prywatności aplikacji TravelQuest. Tekst jest podzielony na sekcje, które jasno wyjaśniają zasady przetwarzania danych osobowych użytkowników.

## 🏗️ Struktura widoku

- Główny kontener to `ScrollView` z tłem ustawionym na kolor `@color/background_main` i paddingiem po bokach.
- Wewnątrz `ScrollView` znajduje się `LinearLayout` (pionowy), który zawiera całą treść polityki.
- Tekst jest podzielony na nagłówki (`TextView` ze stylem `@style/AboutTextH`) i akapity (`TextView` ze stylem `@style/AboutTextp`).
- Tytuł ekranu "Polityka Prywatności" jest wycentrowany, pogrubiony i większy (`24sp`).
- Data wejścia w życie i ostatnia aktualizacja są podane pod tytułem z odstępem.
- Treść polityki jest szczegółowa i sformatowana w punktach, często z użyciem znaków „✓” dla list.

## 🖋️ Style

- `@style/AboutTextH` — styl nagłówków rozdziałów (prawdopodobnie pogrubiony, większa czcionka)
- `@style/AboutTextp` — styl paragrafów (czytelna czcionka, standardowy rozmiar i kolor)
- Kolory tekstu są spójne, głównie ciemne (`#333333`, `@color/black`), co poprawia czytelność.

---

# `fragment_product.xml`
- **Opis**:  
  Layout ekranu szczegółów produktu (np. hotelu) w aplikacji TerraQuest. Zaprojektowany do prezentacji informacji o obiekcie turystycznym wraz z galerią, cenami, opisem i opiniami klientów.

- **Funkcje**:
    - `ScrollView` umożliwia przewijanie całej zawartości ekranu.
    - `ImageView` o ID `hotelImage` wyświetla główne zdjęcie hotelu z zaokrąglonymi narożnikami.
    - `TextView` `hotelName` i `hotelLocation` pokazują nazwę oraz lokalizację hotelu.
    - `TextView` `nightsCount` prezentuje liczbę nocy w formie wyróżnionej etykiety.
    - Sekcja cen z `originalPrice` (przekreślona lub szara cena) i `discountedPrice` (cena promocyjna, wyróżniona kolorem czerwonym).
    - Przycisk `btnReserve` do rezerwacji hotelu.
    - Sekcja "Opis" z możliwością rozwinęcia dodatkowego tekstu (`textOpis`).
    - Bloki z udogodnieniami (`textUdogodnienia`) oraz szczegółowymi informacjami o wyposażeniu (`textInfoOpis`).
    - Sekcja ocen klientów z tytułami i `ViewPager2` (`reviewsViewPager`) do przeglądania opinii.

---

# `fragment_reg.xml`
- **Opis**:  
  Layout ekranu rejestracji użytkownika w aplikacji TerraQuest. Zaprojektowany tak, aby umożliwić wygodne wprowadzenie danych rejestracyjnych oraz przejście do logowania.

- **Funkcje**:
    - `ScrollView` umożliwia przewijanie zawartości na mniejszych ekranach.
    - Sekcja nagłówkowa z logo aplikacji (`logoImage`) oraz nazwą `TerraQuest` (`logoText`).
    - Formularz rejestracyjny z polami:
        - `emailInput` — wprowadzanie adresu e-mail (typ `textEmailAddress`).
        - `passwordInput` — wprowadzanie hasła (typ `textPassword`).
        - `autoLoginCheckBox` — opcja automatycznego logowania.
    - Przycisk `registerButton` do zatwierdzenia rejestracji.
    - Wizualne rozdzielenie sekcji formularza od opcji alternatywnej za pomocą linii i tekstu „lub”.
    - Link `loginLink` umożliwiający przejście do ekranu logowania.
    - Całość utrzymana w jasnej, minimalistycznej kolorystyce z zaokrąglonymi polami tekstowymi i przyciskami.

---

# `fragment_search.xml`

## Opis
Layout ekranu wyszukiwania pokoi hotelowych w aplikacji TerraQuest. Umożliwia użytkownikowi podanie parametrów wyszukiwania, filtrowanie oraz sortowanie wyników, które są wyświetlane w postaci listy.

## Główne elementy i funkcjonalności

- **ScrollView**  
  Zapewnia przewijalność zawartości i wypełnia cały ekran (`match_parent`). Tło ustawione na kolor z zasobów (`@color/background_main`).

- **Nagłówki**
    - `TextView` z komunikatem `"Oto wyniki twojego wyszukiwania"` – duża, pogrubiona czcionka.
    - `TextView` z informacją o porównywaniu cen na wielu stronach – mniejsza, szara czcionka.

- **Sekcja parametrów wyszukiwania (kontener z tłem i cieniem)**
    - Pole tekstowe `input_destination` do wpisania miejsca docelowego podróży (np. "Rzym, Włochy").
    - Dzieląca linia (`View`) oddzielająca miejsce od pozostałych parametrów.
    - Dwa pola tekstowe w układzie poziomym:
        - `editTextDate` – data wyjazdu, nieedytowalne pole (klikane, może otwierać kalendarz).
        - `text_people` – liczba osób (liczbowy input).
    - Przycisk `button_search` do rozpoczęcia wyszukiwania.

- **Przycisk do przełączania widoczności filtrów**
    - `button_toggle_filters` z napisem „Pokaż filtry”.

- **Sekcja filtrów (domyślnie ukryta lub zwijana)**
    - Etykieta "Cena (promocyjna)".
    - Suwak `seekBarPrice` do wyboru maksymalnej ceny, z kolorowymi wskazaniami i suwakiem.
    - Tekst wyświetlający aktualną wartość ceny (`textViewPriceValue`).

- **Sortowanie wyników**
    - Etykieta "Sortuj po:".
    - Trzy przyciski do sortowania:
        - `button_sort_newest` – sortuj po najnowszych ofertach.
        - `button_sort_price_asc` – sortuj rosnąco po cenie.
        - `button_sort_price_desc` – sortuj malejąco po cenie.

- **Lista wyników wyszukiwania**
    - `RecyclerView` (`recyclerViewHotels`) wyświetlający wyniki w formie listy z odstępem na dole.

## Styl i wygląd
- Layout utrzymany w jasnej, przejrzystej kolorystyce z akcentem na pomarańczowe i czarne kolory (np. przyciski i suwaki).
- Zaokrąglone rogi przycisków i pól edycji (zdefiniowane w `@drawable/rounded_button` i `@drawable/edit_text_bg`).
- Cienie i marginesy nadają przestrzenność i estetykę.

---

# `fragment_user.xml`

## Opis
Layout ekranu profilu użytkownika w aplikacji TerraQuest. Zawiera informacje o użytkowniku, ustawienia konta, dodatkowe opcje i historię rezerwacji.

## Główne elementy i funkcjonalności

- **ScrollView**  
  Zapewnia przewijalność zawartości ekranu i wypełnia cały ekran. Tło ustawione na kolor `@color/background_main`.

- **Główny kontener (LinearLayout, pionowy)**  
  Ustawiony na środek poziomy, z paddingiem na górze i dole.

- **Sekcja danych użytkownika:**
    - `ImageView` (`user_image`) – avatar użytkownika (domyślnie ikona `ic_user`).
    - `TextView` (`user_name`) – nazwa użytkownika, duża i pogrubiona.
    - `TextView` (`user_email`) – email użytkownika, mniejsza szara czcionka.
    - Informacja tekstowa z instrukcją aktualizacji nazwy użytkownika.

- **Nagłówek sekcji "Dane użytkownika"**

- **Opcje użytkownika (LinearLayout pionowy z elementami menu):**
    - `time_settings` – opcja wyświetlająca "Czas i godzina (PM)" z ikoną zegara.
    - `profile_update` – możliwość aktualizacji profilu, z ikoną aktualizacji.
    - `logout_button` – wylogowanie, z ikoną logout.

  Wszystkie elementy mają jednolity wygląd, marginesy, padding i tło `@drawable/menu_item_selector` (prawdopodobnie efekt nacisku).

- **Separator (linia pozioma)**

- **Sekcja "Dodatkowe"**
    - `news_active` – status newslettera (czerwony, pogrubiony tekst "Aktywny newsletter" i ikona).
    - `toNewsletter` – możliwość zapisania się do newslettera, z ikoną '@'.

- **Separator (linia pozioma)**

- **Sekcja "Historia rezerwacji"**
    - Nagłówek tekstowy.
    - `booking_container` – kontener pionowy, prawdopodobnie do dynamicznego dodawania elementów historii rezerwacji.

## Styl i wygląd
- Kolory stonowane, z akcentami (np. czerwony status newslettera).
- Elementy menu mają jednolity styl i efekt nacisku (`@drawable/menu_item_selector`).
- Teksty wyraźne, czytelne, z hierarchią rozmiarów i kolorów.
- Całość responsywna, przewijalna, z wyśrodkowaną zawartością.

---

# `fragment_user_menu.xml`

## Opis
Layout ekranu menu konta użytkownika w aplikacji TerraQuest. Zawiera informacje o koncie, sekcje nawigacyjne do innych części aplikacji oraz odnośniki do produktów i informacji o firmie.

## Główne elementy i układ

- **ScrollView**  
  Przewijalny kontener na cały ekran, z tłem `@color/background_main`.

- **Główny LinearLayout (pionowy)**  
  Padding na górze i dole, szerokość `match_parent`, wysokość `wrap_content`.

### Sekcje:

1. **Konto**
    - Nagłówek `TextView` "Konto" - duży, pogrubiony czarny tekst.
    - Podtytuł z opisem korzyści dla członków TerraQuest, mniejszy i szary tekst.
    - Przycisk `btn_reg` — zachęta do logowania lub rejestracji, styl `@style/Log_reg_btn`.

2. **Warto odwiedzić**
    - Nagłówek sekcji.
    - Pozycje menu jako `TextView` z tłem `@drawable/menu_item_selector`:
        - `btn_weather` – Pogoda
        - `btn_contact` – Kontakt

3. **O nas**
    - Nagłówek sekcji.
    - Pozycje menu (także `TextView` z efektem nacisku):
        - `btn_about` – Poznaj nas - TerraQuest
        - `btn_help` – Centrum Pomocy
        - `btn_privacy` – Polityka Prywatności

4. **Nasze produkty**
    - Nagłówek sekcji.
    - Pozycje menu:
        - `btn_website` – Website App
        - `btn_mobile` – Mobile App
        - `btn_desktop` – Desktop App
        - `btn_project` – Projekt

5. **Stopka**
    - Tekst informacyjny o autorach:  
      "Created by Wiktor Tatarynowicz, Filip Berg, Jacek Prokop"  
      mały, szary, wyśrodkowany.

### Styl i wygląd

- Wszystkie interaktywne elementy (pozycje menu) to `TextView` z tłem `menu_item_selector` zapewniającym efekt kliknięcia.
- Marginesy i paddingi dają przejrzysty, czytelny układ.
- Kolory tekstów dobrze kontrastują z tłem: nagłówki ciemne, opisy i stopka szare.
- Separator `View` o wysokości 1dp i jasnoszarym tle oddziela logicznie sekcje.

---

# `fragment_weather.xml`

## Opis
Prosty, informacyjny layout wyświetlający komunikat o trwających pracach technicznych w sekcji pogody.

## Struktura

- **LinearLayout** (główny kontener)
    - Orientacja pionowa, wyśrodkowane elementy (`gravity="center"`).
    - Padding 24dp, pełna szerokość i wysokość (`match_parent`).
    - Tło: `@color/background_weather`.

### Elementy wewnątrz:

1. **ImageView**
    - Id: `maintenance_image`
    - Wymiary: 300dp x 300dp
    - Źródło obrazka: `@drawable/img_1`
    - `scaleType="centerInside"` zapewnia dopasowanie obrazka do rozmiaru bez rozciągania.
    - Dolny margines 24dp.

2. **TextView** (tytuł)
    - Tekst: "Trwają prace techniczne"
    - Rozmiar czcionki: 28sp, pogrubiony (`textStyle="bold"`).
    - Kolor tekstu: `@color/black`.
    - Dolny margines 8dp.

3. **TextView** (podtytuł)
    - Tekst: "Prosimy o cierpliwość"
    - Rozmiar czcionki: 18sp.
    - Kolor tekstu: `@color/black`.

---

# `hotel_item.xml`
- **Opis**:  
  Layout pojedynczego elementu listy hoteli, zawierający zdjęcie, nazwę, lokalizację oraz informacje o cenie i liczbie nocy.

- **Funkcje**:
    - Wyświetla obraz hotelu z dopasowaniem do rozmiaru (centerCrop).
    - Pokazuje nazwę hotelu i lokalizację z ograniczeniem do jednej linii z elipsą.
    - Sekcja cenowa zawiera liczbę nocy, oryginalną cenę (przekreśloną kolorystycznie) oraz cenę po rabacie (czerwona, pogrubiona).
    - Estetyczne marginesy i zaokrąglone tło dla lepszego wyglądu w karuzeli lub liście.

---

# `hotel_item2.xml`
- **Opis**:  
  Layout pojedynczego elementu listy hoteli o szerokości dopasowanej do rodzica, zawierający obraz, nazwę, lokalizację oraz ceny.

- **Funkcje**:
    - Obraz hotelu wypełniający szerokość kontenera z dopasowaniem (centerCrop).
    - Nazwa i lokalizacja hotelu z ograniczeniem do jednej linii i elipsą.
    - Informacje o liczbie nocy oraz cenach: oryginalna (w szarym kolorze) i promocyjna (czerwona, pogrubiona).
    - Marginesy po bokach zapewniające odstęp od innych elementów listy.
    - Zaokrąglone tło i lekki cień (elevation) dla estetyki.

---

# `item_timeline_entry.xml`
- **Opis**:  
  Layout pojedynczego wpisu osi czasu, prezentujący rok, pionową linię z kropką oraz opis wydarzenia.

- **Funkcje**:
    - `TextView` z rokiem (40dp szerokości, pogrubiony tekst).
    - `FrameLayout` zawierający pomarańczową pionową linię i dekoracyjną kropkę oznaczającą punkt na osi czasu.
    - `TextView` z opisem wydarzenia, który zajmuje pozostałą szerokość (layout_weight = 1).
    - Układ poziomy (LinearLayout) umożliwia czytelne rozdzielenie elementów osi czasu.

---

# `recenzja_item.xml`
- **Opis**:  
  Layout pojedynczej recenzji z gwiazdkami, tytułem, treścią oraz informacją o autorze i dacie.

- **Funkcje**:
    - `LinearLayout` poziomy na gwiazdki oceny (`gwiazdkiLayout`).
    - Pogrubiony `TextView` z tytułem recenzji.
    - `TextView` z treścią/opisem recenzji.
    - Sekcja autora: obrazek awatara, imię autora (pogrubione, szary kolor) oraz data recenzji (mała czcionka, szary kolor).
    - Całość otoczona obramowaniem (`@drawable/border_black`) i wypełniona paddingiem 16dp.

---

# `review_item.xml`
- **Opis**:  
  Layout pojedynczej recenzji prezentujący ocenę gwiazdkową, tytuł, treść oraz autora z datą.

- **Funkcje**:
    - Gwiazdki oceny wyświetlane w `TextView` z żółtym kolorem (`#FFC802`).
    - Pogrubiony tytuł recenzji.
    - Tekst z opisem/treścią recenzji.
    - Informacja o autorze i dacie w szarym kolorze (`#777777`).
    - Całość umieszczona na tle z zaokrąglonymi rogami (`@drawable/rounded_background_hotels`), z paddingiem i marginesami oraz lekkim cieniem (`elevation="2dp"`).

---

## Backend:

# `HelpAdapter.java`
- **Opis**:  
  Adapter RecyclerView do wyświetlania listy pytań i odpowiedzi (FAQ) w aplikacji.

- **Funkcje**:
    - Przechowuje listę obiektów `HelpItem` reprezentujących pojedyncze pytanie i odpowiedź.
    - Tworzy i wiąże widoki elementów listy z layoutem `faq_item.xml`.
    - W `onBindViewHolder` ustawia tytuł i treść FAQ na odpowiednich `TextView`.
    - Zawiera wewnętrzną klasę `HelpViewHolder` przechowującą referencje do elementów UI pojedynczego wiersza.

---

# `HelpItem.java`
- **Opis**:  
  Model danych reprezentujący pojedynczy wpis FAQ w aplikacji TerraQuest.

- **Funkcje**:
    - Przechowuje unikalny identyfikator (`id`), tytuł (`title`) oraz treść (`content`) wpisu pomocy.
    - Dostarcza publiczne metody dostępowe (`getId()`, `getTitle()`, `getContent()`) do odczytu pól.

---

# `Hotel.java`
- **Opis**:  
  Model danych reprezentujący hotel w aplikacji TerraQuest.

- **Funkcje**:
    - Przechowuje informacje o nazwie (`name`), lokalizacji (`location`), adresie obrazka (`imageUrl`), cenie oryginalnej (`originalPrice`), cenie po zniżce (`discountedPrice`) oraz liczbie nocy (`nights`).
    - Udostępnia metody dostępowe do odczytu tych właściwości (`getName()`, `getLocation()`, `getImageUrl()`, `getOriginalPrice()`, `getDiscountedPrice()`, `getNights()`).

---

# `HotelAdapter.java`
- **Opis**:  
  Adapter RecyclerView do wyświetlania listy hoteli w aplikacji TerraQuest. Obsługuje różne layouty elementów listy oraz zdarzenia kliknięcia na hotel.

- **Funkcje**:
    - Przyjmuje listę obiektów `Hotel` oraz listener do obsługi kliknięć (`OnHotelClickListener`).
    - Obsługuje różne layouty elementów listy (domyślnie `hotel_item.xml`, można podać inny).
    - W `onBindViewHolder` wiąże dane hotelu z widokami, w tym ładuje obraz z URL przez Glide.
    - Przekazuje kliknięcie użytkownika na element listy przez interfejs `OnHotelClickListener`.
    - ViewHolder zawiera referencje do widoków elementu i metodę `bind` do aktualizacji widoków.

---

# `HotelApiService.java`
- **Opis**:  
  Usługa sieciowa do pobierania danych o hotelach na podstawie nazwy miasta z zewnętrznego API.

- **Funkcje**:
    - Metoda `getHotelsForCity(String cityName, HotelApiCallback callback)` wykonuje asynchroniczne zapytania HTTP w osobnym wątku.
    - Najpierw pobiera `LocationId` miasta przez zapytanie do endpointu `widgetapi.aspx` z parametrem miasta.
    - Następnie na podstawie `LocationId` pobiera oferty hoteli z endpointu `Content.aspx`.
    - Obsługuje błędy sieci i parsowania, przekazując komunikaty do callbacka.
    - Wynik (JSON string z listą hoteli) zwraca w metodzie `onSuccess` callbacka.
    - Loguje odpowiedzi i błędy do logów systemowych.

- **Użycie**:  
  Klasa przyjmuje implementację `HotelApiCallback` do odbioru danych lub obsługi błędów po zakończeniu pobierania.

---

# `Review.java`
- **Opis**:  
  Model danych reprezentujący recenzję użytkownika w aplikacji.

- **Funkcje**:
    - Przechowuje informacje o liczbie gwiazdek (`stars`), tytule recenzji (`title`), treści recenzji (`content`) oraz autorze i dacie (`authorDate`).
    - Zapewnia konstruktor do inicjalizacji wszystkich pól.
    - Udostępnia metody getter do pobierania wartości poszczególnych właściwości.

- **Użycie**:  
  Obiekt `Review` służy do przechowywania i wyświetlania recenzji w interfejsie użytkownika.

---

# `ReviewPagerAdapter.java`
- **Opis**:  
  Adapter RecyclerView do wyświetlania listy recenzji w formie przewijanego widoku (pager).

- **Funkcje**:
    - Inflatuje layout `review_item` dla pojedynczej recenzji.
    - Wiąże dane obiektu `Review` z widokami: gwiazdki, tytuł, treść i autor z datą.
    - Obsługuje listę recenzji (`List<Review>`) i zarządza ilością elementów do wyświetlenia.
    - Przechowuje wewnętrzną klasę `ReviewViewHolder` do trzymania referencji do elementów widoku.

- **Użycie**:  
  Stosowany w RecyclerView do dynamicznego wyświetlania recenzji użytkowników w interfejsie aplikacji.

---

# AboutFragment

Fragment wyświetlający stronę „O nas” aplikacji TerraQuest.  
Zawiera statystyki, oś czasu z kluczowymi wydarzeniami oraz nagłówek z obrazem.  
Służy do zaprezentowania informacji o projekcie i jego historii użytkownikowi.

---

# ContactFragment

Fragment zawierający formularz kontaktowy z polami na imię, nazwisko, e-mail oraz wiadomość.  
Obsługuje walidację danych i wyświetla komunikaty o błędach lub potwierdzenie wysłania formularza.  
Dostosowuje wizualnie wysokość elementu graficznego względem layoutu.

---

# ExploreFragment

Fragment służący do przeglądania i wyszukiwania hoteli oraz wyświetlania opinii użytkowników.  
Obsługuje wybór daty pobytu, wprowadzenie celu podróży i liczby osób, a także subskrypcję newslettera z walidacją adresu e-mail i zapisem do lokalnej bazy danych.  
Ładuje dane hoteli z API i recenzje z lokalnej bazy SQLite, wyświetlając je w listach i pagerze.  
Umożliwia przejście do szczegółów wybranego hotelu oraz do ekranu wyników wyszukiwania.

---

# HelpFragment

Fragment wyświetlający sekcję FAQ (Najczęściej zadawane pytania) z paginacją i możliwością wyszukiwania.  
Wczytuje pytania i odpowiedzi z lokalnej bazy danych oraz pozwala na filtrowanie wyników poprzez dynamiczne wyszukiwanie.  
Umożliwia rozwijanie i zwijanie pojedynczych pozycji FAQ z animacją.  
Implementuje paginację, pokazując ograniczoną liczbę pozycji na stronę i umożliwia nawigację między stronami.

---

# HomeFragment

Fragment ekranu głównego aplikacji turystycznej, który:

- Wyświetla przewijany poziomo widok hoteli (RecyclerView) z danymi pobieranymi z API.
- Pozwala użytkownikowi wybrać datę pobytu poprzez DatePicker.
- Umożliwia wpisanie celu podróży i liczby osób.
- Po kliknięciu przycisku "Szukaj" przechodzi do fragmentu wyników wyszukiwania z wybranymi parametrami.
- Wyświetla statyczne zdjęcia miast oraz banery z możliwością przejścia do innych fragmentów (ExploreFragment, LoginFragment).
- Obsługuje wyświetlanie i aktualizację listy hoteli na podstawie danych JSON z API, wraz z konwersją walut na PLN.
- Używa biblioteki Glide do ładowania obrazów.

Metoda `loadHotels(String cityName)` wykonuje asynchroniczne pobranie hoteli dla podanego miasta, parsuje odpowiedź i aktualizuje widok

---

# LoginFragment

Fragment odpowiedzialny za ekran logowania użytkownika.

## Funkcjonalności

- Wyświetla formularz logowania z polami na email i hasło.
- Hasło jest hashowane przy użyciu SHA-256 przed weryfikacją.
- Sprawdza dane logowania w lokalnej bazie SQLite (`users` tabela).
- Jeśli dane są poprawne:
    - Zapisuje stan zalogowania i email w `SharedPreferences`.
    - Przechodzi do fragmentu `UserFragment`.
- W przypadku niepoprawnych danych wyświetla komunikat błędu i czyści pole hasła.
- Umożliwia przejście do fragmentu rejestracji (`RegisterFragment`) po kliknięciu linku "register".

## Implementacja

- Korzysta z `AppDatabaseHelper` do dostępu do bazy danych.
- Używa `MessageDigest` do hashowania haseł (SHA-256).
- Zamknięcie połączenia z bazą w metodzie `onDestroy()`.

Fragment jest częścią aplikacji i wymaga istnienia odpowiednich layoutów i klas (`RegisterFragment`, `UserFragment`).

---

# ProductFragment

Fragment wyświetlający szczegóły hotelu wraz z galerią recenzji oraz umożliwiający dokonanie rezerwacji.  
Ładuje dane z bazy SQLite i korzysta z SharedPreferences do identyfikacji użytkownika.

---

# RegisterFragment

Fragment rejestracji nowego użytkownika.  
Weryfikuje poprawność e-maila i hasła, zabezpiecza hasło przez SHA-256,  
sprawdza unikalność e-maila w bazie i zapisuje dane użytkownika.  
Pozwala na automatyczne logowanie po rejestracji.

---

# SearchFragment

Fragment umożliwiający wyszukiwanie hoteli według miasta, daty i liczby osób.  
Zawiera filtrowanie po cenie oraz sortowanie wyników.  
Wykorzystuje API do pobierania danych hoteli i wyświetla je w liście.  
Pozwala przejść do szczegółów wybranego hotelu.

---

# User_menuFragment

Fragment menu użytkownika z przyciskami do nawigacji między podstronami aplikacji (pogoda, polityka prywatności, kontakt itp.).  
Pozwala także otworzyć zewnętrzne linki do repozytoriów projektu oraz prototypu w Figma.  
Przycisk rejestracji zmienia się na "Profil" po zalogowaniu i przekierowuje do odpowiedniego fragmentu.

---

# UserFragment

Fragment wyświetlający dane użytkownika oraz jego rezerwacje.

## Funkcjonalności
- Pokazuje aktualny czas (aktualizowany co sekundę)
- Wyświetla email, imię i nazwisko użytkownika pobrane z bazy SQLite
- Pokazuje status newslettera (aktywny/nieaktywny)
- Pozwala na aktualizację imienia i nazwiska przez dialog
- Przycisk wylogowania
- Przejście do fragmentu Explore z przewinięciem do newslettera
- Lista rezerwacji użytkownika z możliwością rozwinięcia szczegółów (daty, ceny)

---

# MainActivity

Główna aktywność aplikacji zarządzająca nawigacją dolnego paska (BottomNavigationView).

## Funkcjonalności
- Inicjalizacja i wyświetlanie fragmentów: HomeFragment, ExploreFragment, User_menuFragment
- Ustawienie domyślnego fragmentu (HomeFragment) przy starcie
- Dynamiczna zmiana tytułu przycisku nawigacji „Profil” lub „Zaloguj” w zależności od statusu logowania (SharedPreferences)
- Obsługa wyboru elementów nawigacji i wymiana fragmentów bez powielania (sprawdzenie czy fragment nie jest już aktualnie wyświetlany)
- Ustawienie jasnego koloru status baru na Androidzie Marshmallow i nowszych

---

# TrapezoidImageView

Custom ImageView wyświetlający obraz w kształcie trapezu.

## Funkcjonalności
- Nadpisuje metodę `onDraw`, aby wyciąć obszar widoku w kształcie trapezu
- Tworzy ścieżkę (Path) trapezu, gdzie górna krawędź jest skrócona do 35% szerokości, a dolna zajmuje całą szerokość
- Przycinanie obrazu do tego kształtu dzięki `canvas.clipPath(path)`

## Konstruktor
- Obsługuje trzy konstruktory wymagane do poprawnej integracji w XML i programowo

## Zastosowanie
- Można użyć w layoutach zamiast standardowego ImageView, aby uzyskać efekt trapezu wizualnie wycinającego obraz

## Technologie
- Android Canvas API
- Path do definiowania niestandardowego kształtu
- AppCompatImageView jako baza klasy

---

## Baza danych:

# `AppDatabaseHelper.java`
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

---

# `DatabaseHelper.java`
- **Opis**:  
  Prosta klasa pośrednicząca do obsługi lokalnej bazy danych w aplikacji TerraQuest. Ułatwia dostęp do metod odczytu i zapisu, wykorzystując `AppDatabaseHelper`.

- **Funkcje**:
    - Inicjalizacja pomocnika bazy danych (`AppDatabaseHelper`)
    - Udostępnienie metod:
        - `getReadableDatabase()` – uzyskanie instancji bazy danych tylko do odczytu
        - `getWritableDatabase()` – uzyskanie instancji bazy danych z możliwością zapisu

---

# `UserDatabaseHelper.java`
- **Opis**:  
  Klasa pomocnicza odpowiedzialna za dostęp do bazy danych użytkowników w aplikacji TerraQuest. Działa jako pośrednik, wykorzystując `AppDatabaseHelper`.

- **Funkcje**:
    - Inicjalizacja obiektu `AppDatabaseHelper`
    - Udostępnienie metod:
        - `getReadableDatabase()` – otwiera bazę danych w trybie tylko do odczytu
        - `getWritableDatabase()` – otwiera bazę danych w trybie do zapisu

---

## Makieta projektu - Figma:
Opis:
Makieta projektu w Figma to wizualne odwzorowanie interfejsu użytkownika aplikacji lub strony internetowej. Dokumentacja ta przedstawia sposób korzystania z makiety w Figma, jej cele, funkcje oraz strukturę, jak również zapewnia informacje dotyczące współpracy zespołowej i organizacji projektu w Figma.

[https://www.figma.com/design/VAEeMmg1rGRkZhTuEwuFnK/Platforma-do-Planowania-Podróży-i-Rezerwacji---TerraQuest?node-id=0-1&t=TFYAgNNcLLNpHKD8-1](https://www.figma.com/proto/VAEeMmg1rGRkZhTuEwuFnK/Platforma-do-Planowania-Podr%C3%B3%C5%BCy-i-Rezerwacji---TerraQuest?node-id=1095-7789&p=f&t=JPO25UM7msW55Rxp-0&scaling=scale-down&content-scaling=fixed&page-id=87%3A1675&starting-point-node-id=1095%3A7789)
---
