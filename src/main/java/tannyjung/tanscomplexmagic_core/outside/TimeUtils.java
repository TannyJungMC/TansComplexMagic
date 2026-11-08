package tannyjung.tanscomplexmagic_core.outside;

import tannyjung.tanscomplexmagic_core.Core;

import java.time.LocalDateTime;

public class TimeUtils {

    private static boolean reset_timestamps = false;

    public static long combineTime (int hour, int minute, int second) {

        return second + (minute * 60L) + (hour * 3600L);

    }

    public static long getRemain (String timestamp) {

        if (reset_timestamps == true) {

            return 0;

        }

        if (timestamp.isEmpty() == true) {

            timestamp = "0";

        }

        long remain = Long.parseLong(timestamp) - Long.parseLong(getTimestampCurrent());

        if (remain < 0) {

            remain = 0;

        }

        return remain;

    }

    public static String getRemainReadable (String timestamp) {

        long remain = getRemain(timestamp);
        String remain_day = "";

        if (remain > 86400) {

            remain_day = ((remain / 86400) % 86400) + "d ";

        }

        String remain_hour = "";

        if (remain > 3600) {

            remain_hour = (((remain / 3600) % 3600) % 24) + "h ";

        }

        String remain_minute = "";

        if (remain > 60) {

            remain_minute = ((remain / 60) % 60) + "m ";

        }

        String remain_second = (remain % 60) + "s";

        return remain_day + remain_hour + remain_minute + remain_second;

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

    public static void resetTimestamps () {

        reset_timestamps = true;

        Core.DelayedWork.createBasic(20, () -> {

            reset_timestamps = false;

        });

    }

}
