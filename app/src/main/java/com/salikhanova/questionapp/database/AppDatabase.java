package com.salikhanova.questionapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.salikhanova.questionapp.dao.AnswerDao;
import com.salikhanova.questionapp.dao.QuestionAnswerDao;
import com.salikhanova.questionapp.dao.QuestionCustomAnswerDao;
import com.salikhanova.questionapp.dao.QuestionDao;
import com.salikhanova.questionapp.entity.Answer;
import com.salikhanova.questionapp.entity.Question;
import com.salikhanova.questionapp.entity.QuestionAnswer;
import com.salikhanova.questionapp.entity.QuestionCustomAnswer;

/**
 * Created by User on 02.03.2018.
 */

@Database(entities = {
        Question.class,
        Answer.class,
        QuestionAnswer.class,
        QuestionCustomAnswer.class
}, version = 10)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();
    public abstract QuestionAnswerDao questionAnswerDao();
    public abstract QuestionCustomAnswerDao questionCustomAnswerDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized(AppDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "questionapp")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5,
                                    MIGRATION_5_6, MIGRATION_6_7, MIGRATION_7_8, MIGRATION_8_9, MIGRATION_9_10)
                            //, MIGRATION_3_4,
                    //MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7, MIGRATION_7_2,
                            //MIGRATION_7_3
                            //.fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        //Toast.makeText(context, "return INSTANCE", Toast.LENGTH_LONG).show();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id))");
            database.execSQL("CREATE INDEX index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id))");
            database.execSQL("CREATE INDEX index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id))");
            database.execSQL("CREATE INDEX index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id))");
            database.execSQL("CREATE INDEX index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };

    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id))");
            database.execSQL("CREATE INDEX index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };

    static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id))");
            database.execSQL("CREATE INDEX index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };

    static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id))");
            database.execSQL("CREATE INDEX index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };

    static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id));");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };

    static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`text` TEXT, `question_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "`question_id` INTEGER, `answer_id` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `QuestionCustomAnswer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`question_id` INTEGER NOT NULL, `answer` TEXT, "
                    + "FOREIGN KEY (question_id) REFERENCES Question(id));");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_QuestionCustomAnswer_question_id ON QuestionCustomAnswer(question_id);");

            database.execSQL("DELETE FROM Answer;");

            database.execSQL("DELETE FROM Question;");

            database.execSQL("INSERT INTO Question (text) VALUES ('Из каких источников вы узнали о ТОО \"ATAYURT\"')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Google', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Реклама в Yandex', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Газета/журнал', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Рекомендация', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('Насколько качественно было обслуживание клиентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее качественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Трудно сказать', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Скорее некачественно', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Очень некачественно', 2)");
            database.execSQL("INSERT INTO Question (text) VALUES('Какого качество нашей продукции (услуг) по сравнению с качеством продукции (услуг) конкурентов?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени лучше', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Сравнительно одинаково', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('В определенной степени хуже', 3)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Намного хуже', 3)");
            database.execSQL("INSERT INTO Question (text) VALUES('Вы бы рекомендовали нашу компанию другим людям?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 4)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 4)");
            database.execSQL("INSERT INTO Question (text) VALUES('Предполагаете ли Вы в дальнейшем приобретать нашу продукцию/услугу?')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Несомненно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Да', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Вероятно нет', 5)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('Безусловно нет', 5)");
        }
    };
}
