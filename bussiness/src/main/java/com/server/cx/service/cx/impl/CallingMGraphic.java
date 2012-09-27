package com.server.cx.service.cx.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserCustomMGraphic;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: yanjianzou
 * Date: 9/27/12
 * Time: 11:38 AM
 */
@ToString
public class CallingMGraphic {
    @Getter @Setter
    private Integer maxPriority = Integer.MIN_VALUE;
    @Getter @Setter
    private Map<Integer,List<MGraphic>> userMGraphicMap;

    public CallingMGraphic(){
        userMGraphicMap = Maps.newHashMap();
    }

    public void put(Integer priority, MGraphic mgraphic) {
        List<MGraphic> mGraphics = userMGraphicMap.get(priority);
        if(mGraphics == null){
            mGraphics = Lists.newArrayList();
        }
        mGraphics.add(mgraphic);
        userMGraphicMap.put(priority,mGraphics);
    }

    public MGraphic retrieveMaxPriorityMGraphic() {
        List<MGraphic> mGraphics  = userMGraphicMap.get(maxPriority);
        if(mGraphics == null || mGraphics.size() == 0){
            return  null;
        }

        Date currentDate = LocalDate.now().toDate();
        if(maxPriority == 5 || maxPriority == 6){
            long MIN= Long.MAX_VALUE;
            MGraphic minMGraphic = null;
            for(MGraphic mGraphic:mGraphics){

                UserCustomMGraphic userCustomMGraphic = (UserCustomMGraphic) mGraphic;
                if(userCustomMGraphic.getBegin() == null || userCustomMGraphic.getEnd() == null){
                    userCustomMGraphic.setBegin(LocalDate.parse("1900-1-1").toDate());
                    userCustomMGraphic.setEnd(LocalDate.parse("2100-1-1").toDate());
                }
                long value = userCustomMGraphic.getEnd().getTime() - currentDate.getTime();
                if(value <= MIN){
                    MIN = value;
                    minMGraphic = mGraphic;
                }

            }
            return minMGraphic;
        }else {
            return mGraphics.get(RandomUtils.nextInt(mGraphics.size()));
        }
    }
}
