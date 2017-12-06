//package net.douglashiura.picon;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//public class TimeCompile {
//
//	public Date compile(String tempo) throws ParseException {
//		if (!(tempo.contains("now") || tempo.contains("+") || tempo
//				.contains("-"))) {
//			String padrao = tempo.length() > 12 ? "yyyy/MM/dd hh:mm"
//					: "yyyy/MM/dd";
//			return new SimpleDateFormat(padrao).parse(tempo);
//		} else if (tempo.equals("now")) {
//			return new Date();
//		} else {
//			String[] parser = tempo.split("/");
//			Calendar calendar = solverYear(parser[0]);
//			solverMonth(calendar, parser[1]);
//			solverDay(calendar, apenasDay(parser[2]));
//			if (parser[2].split(" ").length > 1) {
//				solverTime(calendar, parser[2].split(" ")[1]);
//			}
//			return calendar.getTime();
//		}
//	}
//
//	private void solverTime(Calendar calendar, String timer) {
//		String horaMinuto[] = timer.split(":");
//		calendar.set(Calendar.HOUR, Integer.parseInt(horaMinuto[0]));
//		calendar.set(Calendar.MINUTE, Integer.parseInt(horaMinuto[1]));
//	}
//
//	private void solverDay(Calendar calendar, String dayExpression) {
//		if (dayExpression.contains("+") || dayExpression.contains("-")) {
//			calendar.set(
//					Calendar.DAY_OF_MONTH,
//					calendar.get(Calendar.DAY_OF_MONTH)
//							+ Integer.parseInt(dayExpression));
//		} else {
//			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayExpression));
//		}
//	}
//
//	private String apenasDay(String dayAndTime) {
//		return dayAndTime.split(" ")[0];
//	}
//
//	private void solverMonth(Calendar calendar, String monthExpression) {
//		if (monthExpression.contains("+") || monthExpression.contains("-")) {
//			calendar.set(
//					Calendar.MONTH,
//					calendar.get(Calendar.MONTH)
//							+ Integer.parseInt(monthExpression));
//		} else {
//			calendar.set(Calendar.MONTH, Integer.parseInt(monthExpression));
//		}
//	}
//
//	private Calendar solverYear(String yearExpression) {
//		Calendar calendar = Calendar.getInstance();
//		if (yearExpression.contains("-") || yearExpression.contains("+")) {
//			calendar.set(
//					Calendar.YEAR,
//					calendar.get(Calendar.YEAR)
//							+ Integer.parseInt(yearExpression));
//		} else {
//			calendar.set(Calendar.YEAR, Integer.parseInt(yearExpression));
//		}
//		return calendar;
//	}
//}
package net.douglashiura.picon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompiladorDeTempo {

	private static final DateFormat FORMATO = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm");
	private static final DateFormat FORMATO_SEM_HORAS = new SimpleDateFormat(
			"yyyy/MM/dd");

	public Date compile(String tempo) throws ParseException {
		if (!(tempo.contains("now") || tempo.contains("+") || tempo
				.contains("-"))) {
			DateFormat padrao = tempo.length() > 12 ? FORMATO
					: FORMATO_SEM_HORAS;
			return padrao.parse(tempo);
		} else if (tempo.equals("now")) {
			return new Date();
		} else {
			String[] parser = tempo.split("/");
			Calendar calendar = solverYear(parser[0]);
			solverMonth(calendar, parser[1]);
			solverDay(calendar, apenasDay(parser[2]));
			if (parser[2].split(" ").length > 1) {
				solverTime(calendar, parser[2].split(" ")[1]);
			}
			return calendar.getTime();
		}
	}

	private void solverTime(Calendar calendar, String timer) {
		String horaMinuto[] = timer.split(":");
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaMinuto[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(horaMinuto[1]));
		calendar.set(Calendar.MILLISECOND,0);
		calendar.set(Calendar.SECOND,0);
	}

	private void solverDay(Calendar calendar, String dayExpression) {
		add(calendar, Calendar.DAY_OF_MONTH, dayExpression);
	}

	private String apenasDay(String dayAndTime) {
		return dayAndTime.split(" ")[0];
	}

	private void solverMonth(Calendar calendar, String monthExpression) {
		add(calendar, Calendar.MONTH, monthExpression);
	}

	private Calendar solverYear(String yearExpression) {
		Calendar calendar = Calendar.getInstance();
		add(calendar, Calendar.YEAR, yearExpression);
		return calendar;
	}

	public void add(Calendar calendar, int operacao, String total) {
		int ajustar = seRelativa(calendar, operacao, total);
		calendar.set(operacao, ajustar);
	}

	private int seRelativa(Calendar calendar, int operacao, String total) {
		if (total.startsWith("+") || total.startsWith("-"))
			return calendar.get(operacao) + Integer.parseInt(total);
		else
			return Integer.parseInt(total);
	}
}
