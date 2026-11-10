package tannyjung.tanscomplexmagic_core.outside;

import tannyjung.tanscomplexmagic_core.Core;

import java.time.LocalDateTime;

public class TimeUtils {

    private static boolean reset_timestamps = false;

    public static String convertSecondToReadable (long seconds) {

        if (seconds == 0) {

            return "0s";

        }

        String convert_day = "";

        if (seconds >= 86400) {

            convert_day = ((seconds / 86400) % 86400) + "d ";

        }

        String convert_hour = "";

        if (seconds >= 3600) {

            convert_hour = (((seconds / 3600) % 3600) % 24) + "h ";

        }

        String convert_minute = "";

        if (seconds >= 60) {

            convert_minute = ((seconds / 60) % 60) + "m ";

        }

        String convert_second = (seconds % 60) + "s";
        return convert_day + convert_hour + convert_minute + convert_second;

    }

    public static long combineTime (int hour, int minute, int second) {

        return second + (minute * 60L) + (hour * 3600L);

    }

    public static boolean isPassed (String timestamp) {

        if (reset_timestamps == true) {

            return true;

        }

        return getRemain(timestamp) == 0;

    }

    public static long getInterval (String timestamp) {

        if (timestamp.isEmpty() == true || timestamp.equals("0") == true) {

            return 0;

        }

        return Math.abs(Long.parseLong(getTimestampCurrent()) - Long.parseLong(timestamp));

    }

    public static long getRemain (String timestamp) {

        if (timestamp.isEmpty() == true || timestamp.equals("0") == true) {

            return 0;

        }

        long remain = Long.parseLong(timestamp) - Long.parseLong(getTimestampCurrent());

        if (remain < 0) {

            remain = 0;

        }

        return remain;

    }

    public static String getTimestampCurrent () {

        long timestamp = LocalDateTime.now().getDayOfYear() * 31536000L;
        timestamp = timestamp + (LocalDateTime.now().getDayOfYear() * 86400L);
        timestamp = timestamp + (LocalDateTime.now().getHour() * 3600L);
        timestamp = timestamp + (LocalDateTime.now().getMinute() * 60L);
        timestamp = timestamp + LocalDateTime.now().getSecond();
        return String.valueOf(timestamp);

    }

    public static String getTimestampEvery (int hour, int minute, int second) {

        long every = combineTime(hour, minute, second);

        if (every == 0) {

            return "0";

        }

        long remain = Long.parseLong(getTimestampCurrent()) + every;
        remain = (long) Math.floor((double) remain / every);
        remain = remain * every;
        return String.valueOf(remain);

    }

    public static String addTimeToTimestamp (String timestamp, int hour, int minute, int second) {

        long convert = 0;

        if (timestamp.isEmpty() == false) {

            convert = Long.parseLong(timestamp);

        }

        return String.valueOf(convert + combineTime(hour, minute, second));

    }

    public static void resetTimestampEvery () {

        reset_timestamps = true;

        Core.DelayedWork.createBasic(20, () -> {

            reset_timestamps = false;

        });

    }

}
