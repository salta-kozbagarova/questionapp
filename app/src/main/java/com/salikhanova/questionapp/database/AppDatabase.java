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
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();
    public abstract QuestionAnswerDao questionAnswerDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            Toast.makeText(context, "-------------------------NEW INSTANCE", Toast.LENGTH_LONG).show();
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "questionapp")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
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
            database.execSQL("CREATE TABLE `Question` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE `Answer` (`id` INTEGER, "
                    + "`name` TEXT, `question_id` INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE `QuestionAnswer` (`id` INTEGER, "
                    + "`question_id` INTEGER, `answer_id` INTEGER, PRIMARY KEY(`id`))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("INERT INTO TABLE Question(id, text) VALUES(1, 'чее')");
            database.execSQL("INERT INTO TABLE Answer(text, question_id) "
                    + "VALUES('через плечоооо', 1)");
            database.execSQL("INERT INTO TABLE Answer(text, question_id) "
                    + "VALUES('через плечоооо 2', 1)");
            database.execSQL("INERT INTO TABLE Answer(text, question_id) "
                    + "VALUES('через плечоооо 3', 1)");
            database.execSQL("INERT INTO TABLE Answer(text, question_id) "
                    + "VALUES('через плечоооо 4', 1)");
        }
    };
}
