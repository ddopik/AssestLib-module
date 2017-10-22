package defaultIntializarion;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ddopik on 10/11/2017.
 */

//Because T is not a value -
// ---->it's just a hint for the compiler. The JVM has no clue of the T.
// You can use generics only as a type for the purposes of type checking at compile tim

public class Utilization {


    ///getting instance of the giving String
//    Note that you need to provide the fully qualified name of the class for the class loader to find it.
//    I.e., if 'class' is actually in some package, you need to do forName("your.package.A") for it to work.
    public Class<?> getClassInstance(String className) throws ClassNotFoundException {

        return Class.forName(className);
    }

    public String getCurrentDate(String flag) {
        Calendar c = Calendar.getInstance();


        switch (flag) {
            case "Day":
                SimpleDateFormat d = new SimpleDateFormat("dd");
                return d.format(c.getTime());

            case "Day_word":
                SimpleDateFormat d_w = new SimpleDateFormat("EEEE");
                return d_w.format(c.getTime());

            case "Month_word":
                SimpleDateFormat m = new SimpleDateFormat("MMM");
                return m.format(c.getTime());

            case "Month":
                SimpleDateFormat m_w = new SimpleDateFormat("MM");
                return m_w.format(c.getTime());


            case "Year":
                SimpleDateFormat y = new SimpleDateFormat("yyyy");
                return y.format(c.getTime());

            default:
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(c.getTime());
                return formattedDate;
        }


    }
}
