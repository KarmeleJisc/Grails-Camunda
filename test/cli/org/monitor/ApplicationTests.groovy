package org.monitor

import grails.test.AbstractCliTestCase

class ApplicationTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testOrgMonitorApplication() {

        execute(["org-monitor-application"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
