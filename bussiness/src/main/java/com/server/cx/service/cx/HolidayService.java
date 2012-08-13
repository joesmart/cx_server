package com.server.cx.service.cx;

import com.server.cx.entity.cx.HolidayType;

import java.util.Date;

/**
 * User: yanjianzou
 * Date: 12-8-13
 * Time: 上午9:54
 * FileName:HolidayService
 */
public interface HolidayService {

    public Date getAppropriateHolidayDate(HolidayType holidayType);
}
