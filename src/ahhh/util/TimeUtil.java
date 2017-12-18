package ahhh.util;

public class TimeUtil {
	public static String durationToString(long msec_time, boolean dec_seconde) {
		int j = 0, h = 0, m = 0, s = 0;
		long msec = msec_time;

		j = (int) (msec / (1000 * 60 * 60 * 24));
		msec -= (long) (1000 * 60 * 60 * 24) * j;
		h = (int) (msec / (1000 * 60 * 60));
		msec -= (long) (1000 * 60 * 60) * h;
		m = (int) (msec / (1000 * 60));
		msec -= (long) (1000 * 60) * m;
		s = (int) (msec / 1000);
		msec -= (long) 1000 * s;

		String result = "";
		if (j > 0) result = result.concat(j + "j ");
		if (h > 0) result = result.concat(h + "h ");
		if (m > 0) result = result.concat(m + "m ");
		if (s > 0 && !dec_seconde) result = result.concat(s + "s");
		else if (dec_seconde && (s > 0 || msec > 0)) {
			msec += s * 1000;
			result = result.concat((msec / 1000D) + "s");
		}

		if (result.equals("")) result = "0";

		return result.trim();
	}

	public static String durationToString(long msec_time) {
		return durationToString(msec_time, false);
	}

}
