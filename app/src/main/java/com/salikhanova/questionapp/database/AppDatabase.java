package com.salikhanova.questionapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.widget.Toast;

import com.salikhanova.questionapp.dao.AnswerDao;
import com.salikhanova.questionapp.dao.QuestionAnswerDao;
import com.salikhanova.questionapp.dao.QuestionDao;
import com.salikhanova.questionapp.entity.Answer;
import com.salikhanova.questionapp.entity.Question;
import com.salikhanova.questionapp.entity.QuestionAnswer;

/**
 * Created by User on 02.03.2018.
 */

@Database(entities = {
        Question.class,
        Answer.class,
        QuestionAnswer.class
}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();
    public abstract QuestionAnswerDao questionAnswerDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized(AppDatabase.class){
                if (INSTANCE == null) {
                    //Toast.makeText(context, "-------------------------NEW INSTANCE", Toast.LENGTH_LONG).show();
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "questionapp")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
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
            database.execSQL("DROP TABLE IF EXISTS Question; CREATE TABLE `Question` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");
            database.execSQL("DROP TABLE IF EXISTS Answer; CREATE TABLE `Answer` (`id` INTEGER, "
                    + "`name` TEXT, `question_id` INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("DROP TABLE IF EXISTS QuestionAnswer; CREATE TABLE `QuestionAnswer` (`id` INTEGER, "
                    + "`question_id` INTEGER, `answer_id` INTEGER, PRIMARY KEY(`id`))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO Question (text) VALUES ('чее')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 2', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 3', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 4', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('чее 22222')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо  2222', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 2  2222', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 3  2222', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 4  2222', 2)");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS Question; CREATE TABLE `Question` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");
            database.execSQL("DROP TABLE IF EXISTS Answer; CREATE TABLE `Answer` (`id` INTEGER, "
                    + "`name` TEXT, `question_id` INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("DROP TABLE IF EXISTS QuestionAnswer; CREATE TABLE `QuestionAnswer` (`id` INTEGER, "
                    + "`question_id` INTEGER, `answer_id` INTEGER, PRIMARY KEY(`id`))");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO Question (text) VALUES ('чее')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 2', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 3', 1)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 4', 1)");
            database.execSQL("INSERT INTO Question (text) VALUES('чее 22222')");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо  2222', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 2  2222', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 3  2222', 2)");
            database.execSQL("INSERT INTO Answer (text, question_id) "
                    + "VALUES('через плечоооо 4  2222', 2)");
        }
    };
}
