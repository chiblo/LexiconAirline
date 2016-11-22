package enumeration;

/**
 * Created by User on 2016-07-13.
 */
public class Distance {
    public static int takeDistance(City startingPoint, City destination) {
        switch (startingPoint) {

            case STOCKHOLM:
                switch (destination) {
                    case ROME: {
                        return 25000;
                    }
                    case BERLIN: {
                        return 10000;
                    }
                    case PARIS: {
                        return 19000;
                    }
                }
            case ROME:
                switch (destination) {
                    case STOCKHOLM: {
                        return 25000;
                    }
                    case BERLIN: {
                        return 15000;
                    }
                    case PARIS: {
                        return 14000;
                    }
                }
            case BERLIN:
                switch (destination) {
                    case ROME: {
                        return 15000;
                    }
                    case STOCKHOLM: {
                        return 10000;
                    }
                    case PARIS: {
                        return 11000;
                    }
                }
            case PARIS:
                switch (destination) {
                    case ROME: {
                        return 14000;
                    }
                    case BERLIN: {
                        return 11000;
                    }
                    case STOCKHOLM: {
                        return 19000;
                    }
                }
        }
        return 10000;
    }
}
