package io.github.jhipster.application.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieNight {
    public static Boolean canViewAll(List<Movie> movies) {

        boolean canViewAll = true;

        if (movies.size() == 1) {
            canViewAll = true;
        }

        for (int i = 0; i < movies.size() - 1; i++) {

            for (int j = 1; j < movies.size(); j++) {

                if (movies.get(i).getStart().after(movies.get(j).getStart())
                    && movies.get(i).getStart().before(movies.get(j).getEnd())) {
                    return false;
                }

                if(movies.get(i).getEnd().before(movies.get(j).getEnd())
                    && movies.get(i).getEnd().after(movies.get(j).getStart())){
                    return false;
                }

            }
        }

        return canViewAll;

    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("y-M-d H:m");

        ArrayList<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie(sdf.parse("2015-01-01 20:00"), sdf.parse("2015-01-01 21:30")));
        movies.add(new Movie(sdf.parse("2015-01-01 23:10"), sdf.parse("2015-01-01 23:30")));
        movies.add(new Movie(sdf.parse("2015-01-01 16:30"), sdf.parse("2015-01-01 18:00")));
        movies.add(new Movie(sdf.parse("2015-01-01 21:30"), sdf.parse("2015-01-01 23:00")));
        movies.add(new Movie(sdf.parse("2015-01-01 19:00"), sdf.parse("2015-01-01 20:00")));
        movies.add(new Movie(sdf.parse("2015-01-01 18:00"), sdf.parse("2015-01-01 19:00")));

        System.out.println(MovieNight.canViewAll(movies));
    }
}

class Movie {

    private Date start, end;

    public Movie(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }
}
