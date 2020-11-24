
public class MovieDbRunner {  
  public static void main(String[] args) {
    MovieDatabase mdb = new MovieDatabase("moviedb");
    mdb.getMoviesOf("Chris Evans");
  }
}
