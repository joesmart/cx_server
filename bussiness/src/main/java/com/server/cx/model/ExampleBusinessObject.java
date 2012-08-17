package com.server.cx.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExampleBusinessObject {

        public void doIt() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String str = format.format(new Date());
            System.out.println(str + "Into ExampleBusinessObject");
        }
}
