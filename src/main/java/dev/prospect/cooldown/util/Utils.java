package dev.prospect.cooldown.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.time.DurationFormatUtils;

@UtilityClass
public class Utils {

    public String getPrettierDuration(long millis) {
        return DurationFormatUtils.formatDurationWords(millis, true, true);
    }
}