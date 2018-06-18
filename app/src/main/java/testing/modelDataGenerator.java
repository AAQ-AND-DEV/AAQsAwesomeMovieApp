package testing;

import com.aaqanddev.aaqsawesomeandroidapp.pojo.AaqMovie;

import java.util.ArrayList;
import java.util.List;

public class modelDataGenerator {

    public List<AaqMovie> createTestMovies(){
        List<AaqMovie> movies =  new ArrayList<>();
        movies.add(new AaqMovie("test one"));
        movies.add(new AaqMovie("test 2"));
        movies.add(new AaqMovie("test  3"));
        movies.add(new AaqMovie("test4"));
        return movies;
    }

}
