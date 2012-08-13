package com.server.cx.service.cx.impl;

import com.server.cx.dao.cx.HolidayDao;
import com.server.cx.dao.cx.spec.HolidaySpecifications;
import com.server.cx.entity.cx.Holiday;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.service.cx.HolidayService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-13
 * Time: 上午9:57
 * FileName:HolidayTypeImpl
 */
@Service(value = "holidayService")
@Transactional
public class HolidayServiceImpl implements HolidayService{

    @Autowired
    private HolidayDao holidayDao;

    @Override
    public Date getAppropriateHolidayDate(HolidayType holidayType) {
        List<Holiday> holidays =  holidayDao.findAll(HolidaySpecifications.getCurrentValidaHolidayQueryByHolidayTypeAndCurrentDate(holidayType, LocalDate.now().toDate()), new Sort(Sort.Direction.ASC, "id"));
        if(holidays != null && holidays.size() > 0){
            return holidays.get(0).getDay();
        }
        return null;
    }
}
