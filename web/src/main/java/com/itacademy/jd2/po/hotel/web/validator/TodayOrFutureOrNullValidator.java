package com.itacademy.jd2.po.hotel.web.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TodayOrFutureOrNullValidator implements ConstraintValidator<TodayOrFutureOrNull, Date> {
    public final void initialize(final TodayOrFutureOrNull annotation) {
    }

    public final boolean isValid(final Date value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Calendar calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Date d = calendar.getTime();
        boolean b = d.before(value) || d.equals(value);
        return b;
    }
}
