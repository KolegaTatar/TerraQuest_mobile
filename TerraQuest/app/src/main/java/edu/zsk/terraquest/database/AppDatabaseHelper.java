package edu.zsk.terraquest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "terraquest.db";
    private static final int DATABASE_VERSION = 5;

    public static final String TABLE_USERS = "users";
    public static final String TABLE_REVIEWS = "reviews_terraQuest";
    public static final String TABLE_HELP = "help";
    public static final String TABLE_RESERVATION = "reservation";
    public static final String TABLE_DATE = "date";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT UNIQUE," +
                "password TEXT," +
                "first_name TEXT," +
                "last_name TEXT," +
                "newsletter INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_REVIEWS + " (" +
                "review_id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "description TEXT," +
                "reviewer TEXT," +
                "date TEXT," +
                "rating INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HELP + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RESERVATION + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "hotel_name TEXT," +
                "check_in TEXT," +
                "check_out TEXT," +
                "guests INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES users(id))");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DATE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +
                "value TEXT)");

        insertInitialReviews(db);
        insertInitialHelpData(db);
    }

    private void insertInitialReviews(SQLiteDatabase db) {
        insertReview(db, 1, "Świetny hotel", "Naprawdę pokochałem ten hotel – czysto, komfortowo i świetna lokalizacja.", "Jan", "2025-04-10", 5);
        insertReview(db, 2, "Dobry, ale mógłby być lepszy", "Hotel w porządku, ale pokoje mogłyby być lepiej wyciszone i śniadanie bogatsze.", "Joanna", "2025-04-09", 3);
        insertReview(db, 3, "Nie wart swojej ceny", "Hotel nie spełnił moich oczekiwań – głośno i niewygodne łóżko.", "Michał", "2025-04-08", 2);
        insertReview(db, 4, "Pobyt idealny!", "Zdecydowanie wart swojej ceny – rewelacyjna obsługa i widok z pokoju.", "Anna", "2025-04-07", 5);
        insertReview(db, 5, "Bardzo udany pobyt", "Jestem zadowolony z zakwaterowania – wszystko zgodne z opisem.", "Krzysztof", "2025-04-06", 4);
        insertReview(db, 6, "Nie najgorzej", "Hotel działa sprawnie, ale bez żadnych wyjątkowych udogodnień.", "Szymon", "2025-04-05", 3);
        insertReview(db, 7, "Niesamowite doświadczenie", "Hotel przerósł moje oczekiwania – basen i spa były genialne.", "Oliwia", "2025-04-11", 5);
        insertReview(db, 8, "Poprawny pobyt", "Spełnia podstawowe wymagania, ale bez rewelacji.", "Mateusz", "2025-04-10", 3);
        insertReview(db, 9, "Rozczarowanie", "Hotel nie spełnił obietnic z opisu – brak klimatyzacji i hałas w nocy.", "Ewa", "2025-04-09", 2);
        insertReview(db, 10, "Wysoki standard", "Świetna obsługa, nowoczesne pokoje i dobre śniadanie. Polecam!", "Tomasz", "2025-04-08", 5);
        insertReview(db, 11, "Średnio", "Pomysł fajny – lokalizacja super, ale pokój wymagał remontu.", "Agnieszka", "2025-04-07", 3);
        insertReview(db, 12, "Tragiczny pobyt", "Po dwóch nocach zrezygnowałem – zimna woda i brak ogrzewania.", "Bartek", "2025-04-06", 1);
        insertReview(db, 13, "Ogólnie w porządku", "Małe niedociągnięcia, ale ogólnie jestem zadowolony z rezerwacji.", "Natalia", "2025-04-05", 4);
        insertReview(db, 14, "Luksus na każdą kieszeń", "Zaskakująco wysoki standard za tę cenę. Polecam każdemu!", "Jakub", "2025-04-04", 5);
        insertReview(db, 15, "Obsługa pierwsza klasa", "Personel bardzo pomocny, szybkie zameldowanie i miła atmosfera.", "Karolina", "2025-04-03", 2);
        insertReview(db, 16, "Brudno i zaniedbane", "Pokój był nieposprzątany, a łazienka śmierdziała wilgocią.", "Marcin", "2025-04-02", 4);
        insertReview(db, 17, "Weekend udany", "Dobra lokalizacja, wygodne łóżko, ale śniadania monotonne.", "Zofia", "2025-04-01", 3);
        insertReview(db, 18, "Nie polecam", "Hałas, brak ciepłej wody, a w pokoju czuć było dym papierosowy.", "Ola", "2025-04-11", 5);
        insertReview(db, 19, "Świetny hotel rodzinny", "Dzieci miały zabawę, a dorośli spokój. Idealne na rodzinny wypad.", "Kuba", "2025-04-11", 3);
        insertReview(db, 20, "Piękny widok z okna", "Z pokoju rozciągał się niesamowity widok na góry. Coś pięknego!", "Agnieszka", "2025-04-12", 5);
        insertReview(db, 21, "Pobyt z problemami", "Dwa razy brak prądu, raz brak wody. Obsługa przepraszała, ale niesmak pozostał.", "Tomek", "2025-04-12", 2);
        insertReview(db, 22, "Czysto i przytulnie", "Mały, ale bardzo zadbany hotelik. Idealny na krótki pobyt.", "Paweł", "2025-04-13", 4);
        insertReview(db, 23, "Nie wrócę tam", "Brak reakcji na zgłoszenia, nieuprzejma recepcja.", "Natalia", "2025-04-13", 5);
        insertReview(db, 24, "Prawie perfekcyjnie", "Wszystko super, tylko trochę słaby internet w pokojach.", "Marek", "2025-04-14", 2);
        insertReview(db, 25, "Warte każdej złotówki", "Za tę cenę – genialne warunki, czystość i komfort na najwyższym poziomie.", "Emilia", "2025-04-14", 4);
        insertReview(db, 26, "Klimat jak w domu", "Rodzinna atmosfera i przyjazny personel. Czułem się jak u siebie.", "Filip", "2025-04-15", 4);
        insertReview(db, 27, "Nieprzyjemne doświadczenie", "Pająki w pokoju, stęchłe ręczniki – nie do zaakceptowania.", "Magda", "2025-04-15", 3);
        insertReview(db, 28, "Bez zastrzeżeń", "Wszystko działało jak należy – dokładnie to, czego oczekiwałem.", "Basia", "2025-04-15", 1);
        insertReview(db, 29, "Fantastyczny personel", "Uprzejmi, uśmiechnięci i zawsze pomocni. Czysta przyjemność!", "Michał", "2025-04-16", 4);
        insertReview(db, 30, "Hotel z klimatem", "Stylowe wnętrze i świetna muzyka w lobby. Idealne miejsce na relaks.", "Weronika", "2025-04-16", 5);
        insertReview(db, 31, "Pokój niezgodny z rezerwacją", "Zarezerwowałem pokój z balkonem, dostałem bez. Brak rekompensaty.", "Adrian", "2025-04-16", 4);
        insertReview(db, 32, "Dobre na jedną noc", "Idealne na przystanek w podróży, ale nie na dłuższy pobyt.", "Karolina", "2025-04-17", 5);
        insertReview(db, 33, "Bardzo miły pobyt", "Pokój codziennie sprzątany, wszystko działało bez zarzutu.", "Kamil", "2025-04-17", 3);
        insertReview(db, 34, "Odradzam rezerwację", "Hotel wyglądał zupełnie inaczej niż na zdjęciach. Rozczarowanie.", "Daria", "2025-04-17", 3);
    }

    private void insertInitialHelpData(SQLiteDatabase db) {
        insertHelp(db, 1, "Jak mogę zarezerwować nocleg?", "Aby zarezerwować nocleg, wybierz interesujący Cię obiekt, kliknij \"Zarezerwuj teraz\" i postępuj zgodnie z instrukcjami płatności.");
        insertHelp(db, 2, "Czy płatność jest bezpieczna?", "Tak, korzystamy z szyfrowanych połączeń SSL oraz bezpiecznych operatorów płatności. Twoje dane są w pełni chronione.");
        insertHelp(db, 3, "Czy muszę zakładać konto, aby zarezerwować?", "Tak, rejestracja konta jest wymagana, aby zarządzać rezerwacjami i otrzymywać powiadomienia.");
        insertHelp(db, 4, "Co zrobić, jeśli właściciel nie odpowiada?", "Skontaktuj się z naszym działem obsługi klienta, a my pomożemy w rozwiązaniu problemu.");
        insertHelp(db, 5, "Czy ceny na stronie są ostateczne?", "Większość cen zawiera podatki i opłaty, jednak w niektórych przypadkach mogą być doliczone dodatkowe koszty, np. opłata klimatyczna.");
        insertHelp(db, 6, "Jakie metody płatności akceptujecie?", "Akceptujemy płatności kartami Visa, MasterCard, PayPal oraz szybkie przelewy bankowe.");
        insertHelp(db, 7, "Kto w filmie Minecraft Movie krzyczy: \"Chicken Jockey!!\"?", "Taką kwestię dialogową wypowiada w filmie Jack Black, który wciela się w rolę Steve'a z Minecraft.");
        insertHelp(db, 8, "W jakim kraju zanotowano największą inflację w 2024 roku?", "Została ona zanotowana w Zimbabwe w południowej części Afryki. Wynosi ona rekordowe 16.00%");
        insertHelp(db, 9, "Czy Cezary Lossy urodził się w Ukrainie?", "Z raportów policji wyczytać można, że Cezary Lossy nie został urodzony na Ukrainie oraz posiada Polskie obywatelstwo.");
        insertHelp(db, 10, "Czy mogę zarezerwować więcej niż jeden pokój na raz?", "Tak, nasza strona umożliwia rezerwacje więcej niż jednego lokalu.");
        insertHelp(db, 11, "Jak długo przed przyjazdem mogę zarezerwować pokój?", "Nie mamy konkretnego zakresu. Pokój można wynająć nawet dzień przed przyjazdem!");
        insertHelp(db, 12, "Jak mogę anulować lub zmienić moją rezerwację?", "Rezerwację usuwa się poprzez przycisk \"Usuń rezerwację\" w zakładce Profil.");
        insertHelp(db, 13, "Czy w pokojach jest dostępne darmowe Wi-Fi?", "Wszelkie szczegółowe informacje zawarte są w opisie danego pokoju.");
        insertHelp(db, 14, "Co zrobić, jeśli mam problem z rezerwacją?", "W razie jakichkolwiek problemów zachęcamy do kontaktu z nami poprzez wypełnienie formularza w zakładce Kontakt.");
        insertHelp(db, 15, "Ile to 9 + 10", "21 :OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        insertHelp(db, 17, "Dlaczego komputerowi zawsze jest zimno?", "Bo pracuje z otwartym oknem.");
        insertHelp(db, 18, "Czy Profesor Renk sprzedała papierosy Kruszwilowi?", "Z relacji naszych pracowników wynika, że taka sytuacja faktycznie miała miejce.");
        insertHelp(db, 19, "Czy mogę zarezerwować pokój bez rejestracji konta?", "Niestety nie ma możliwości rezerwacji hoteli bez rezerwacji.");
        insertHelp(db, 20, "Czy mogę poprosić o dodatkowe łóżko lub łóżeczko dziecięce?", "Takie informacje zależne są od lokalu. W takim przypadku prosimy kontaktować się bezpośrednio z właścicielem pokoju.");
        insertHelp(db, 21, "Co zrobić, jeśli strona rezerwacji nie działa?", "Jeśli strona rezerwacji nie działa, spróbuj odświeżyć stronę lub użyć innej przeglądarki. W razie dalszych problemów skontaktuj się z naszym działem wsparcia.");
    }

    private void insertReview(SQLiteDatabase db, int id, String title, String description, String reviewer, String date, int rating) {
        db.execSQL("INSERT INTO " + TABLE_REVIEWS + " (review_id, title, description, reviewer, date, rating) VALUES (?, ?, ?, ?, ?, ?)",
                new Object[]{id, title, description, reviewer, date, rating});
    }

    private void insertHelp(SQLiteDatabase db, int id, String title, String content) {
        db.execSQL("INSERT INTO " + TABLE_HELP+ " (id, title, content) VALUES (?, ?, ?)",
                new Object[]{id, title, content});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HELP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE);
        onCreate(db);
    }
}
