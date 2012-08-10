package com.server.cx.functional.resource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ContactsResourceTest.class,
        MyCollectionsResourceIT.class,
        SuggestionResourceTest.class,
        VersionInfoResourceTest.class,
        CXAppResourceTest.class,
        HolidayTypeResourceTest.class,
        StatusTypeResourceTest.class,
        ActionResourceTest.class,
        StatusTypeResourceTest.class
    })
public class ResourceSuiteTest {

}
