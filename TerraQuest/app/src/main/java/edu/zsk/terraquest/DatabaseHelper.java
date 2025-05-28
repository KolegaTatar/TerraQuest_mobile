package edu.zsk.terraquest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "terraquest.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_REVIEWS = "reviews_terraQuest";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_REVIEWS =
                "CREATE TABLE " + TABLE_REVIEWS + " (" +
                        "review_id INTEGER PRIMARY KEY, " +
                        "title TEXT, " +
                        "description TEXT, " +
                        "reviewer TEXT, " +
                        "date TEXT, " +
                        "rating INTEGER)";
        db.execSQL(CREATE_TABLE_REVIEWS);

        insertInitialReviews(db);
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

    private void insertReview(SQLiteDatabase db, int id, String title, String description, String reviewer, String date, int rating) {
        ContentValues values = new ContentValues();
        values.put("review_id", id);
        values.put("title", title);
        values.put("description", description);
        values.put("reviewer", reviewer);
        values.put("date", date);
        values.put("rating", rating);
        db.insert(TABLE_REVIEWS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        onCreate(db);
    }
}
