package itstep.edu.contoller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SQLiteController extends SQLiteOpenHelper
{

    public SQLiteController(@Nullable Context context, @Nullable String name, int version)
    {
        super(context, name,null,  version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table Contacts(_id INTEGER primary key autoincrement, " +
                "_name varchar(20), _phone varchar(13))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
