/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2015  Tanaguru.org
 *
 * This file is part of Tanaguru.
 *
 * Tanaguru is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact us by mail: tanaguru AT tanaguru DOT org
 */
package org.opens.tanaguru.rules.rgaa30;

import org.opens.tanaguru.entity.audit.ProcessRemark;
import org.opens.tanaguru.entity.audit.ProcessResult;
import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.rules.rgaa30.test.Rgaa30RuleImplementationTestCase;
import org.opens.tanaguru.rules.keystore.RemarkMessageStore;

/**
 *
 * @author jkowalczyk
 */
public class Rgaa30Rule090101Test extends Rgaa30RuleImplementationTestCase {
    
    public Rgaa30Rule090101Test(String testName) {
        super(testName);
    }

    @Override
    protected void setUpRuleImplementationClassName() {
        setRuleImplementationClassName(
                "org.opens.tanaguru.rules.rgaa30.Rgaa30Rule090101");
    }

    @Override
    protected void setUpWebResourceMap() {
        getWebResourceMap().put("Rgaa30.Test.09.01.01-1Passed-01",
                getWebResourceFactory().createPage(
                getTestcasesFilePath() + "rgaa30/Rgaa30Rule090101/Rgaa30.Test.09.01.01-1Passed-01.html"));
        getWebResourceMap().put("Rgaa30.Test.09.01.01-1Passed-02",
                getWebResourceFactory().createPage(
                getTestcasesFilePath() + "rgaa30/Rgaa30Rule090101/Rgaa30.Test.09.01.01-1Passed-02.html"));
        getWebResourceMap().put("Rgaa30.Test.09.01.01-1Passed-A01",
                getWebResourceFactory().createPage(
                getTestcasesFilePath() + "rgaa30/Rgaa30Rule090101/Rgaa30.Test.09.01.01-1Passed-A01.html"));
        getWebResourceMap().put("Rgaa30.Test.09.01.01-2Failed-01",
                getWebResourceFactory().createPage(
                getTestcasesFilePath() + "rgaa30/Rgaa30Rule090101/Rgaa30.Test.09.01.01-2Failed-01.html"));
        getWebResourceMap().put("Rgaa30.Test.09.01.01-2Failed-02",
                getWebResourceFactory().createPage(
                getTestcasesFilePath() + "rgaa30/Rgaa30Rule090101/Rgaa30.Test.09.01.01-2Failed-02.html"));
        getWebResourceMap().put("Rgaa30.Test.09.01.01-2Failed-A01",
                getWebResourceFactory().createPage(
                getTestcasesFilePath() + "rgaa30/Rgaa30Rule090101/Rgaa30.Test.09.01.01-2Failed-A01.html"));
    }

    @Override
    protected void setProcess() {
        //----------------------------------------------------------------------
        //------------------------------1Passed-01------------------------------
        //----------------------------------------------------------------------
        ProcessResult processResult = processPageTest("Rgaa30.Test.09.01.01-1Passed-01");
        // check test result
        assertEquals(TestSolution.PASSED, processResult.getValue());
        // check test has no remark
        assertNull(processResult.getRemarkSet());
        // check number of elements in the page
        assertEquals(1, processResult.getElementCounter());
        
        
        //----------------------------------------------------------------------
        //------------------------------1Passed-02------------------------------
        //----------------------------------------------------------------------
        processResult = processPageTest("Rgaa30.Test.09.01.01-1Passed-02");
        // check test result
        assertEquals(TestSolution.PASSED, processResult.getValue());
        // check test has no remark
        assertNull(processResult.getRemarkSet());
        // check number of elements in the page
        assertEquals(2, processResult.getElementCounter());
        
        
        //----------------------------------------------------------------------
        //------------------------------1Passed-A01-----------------------------
        //----------------------------------------------------------------------
        processResult = processPageTest("Rgaa30.Test.09.01.01-1Passed-A01");
        // check test result
        assertEquals(TestSolution.PASSED, processResult.getValue());
        // check test has no remark
        assertNull(processResult.getRemarkSet());
        // check number of elements in the page
        assertEquals(1, processResult.getElementCounter());
        

        //----------------------------------------------------------------------
        //------------------------------2Failed-01------------------------------
        //----------------------------------------------------------------------
        processResult = processPageTest("Rgaa30.Test.09.01.01-2Failed-01");
        // check number of elements in the page
        assertEquals(0, processResult.getElementCounter());
        // check test result
        assertEquals(TestSolution.FAILED, processResult.getValue());
        // check number of remarks and their value
        assertEquals(1, processResult.getRemarkSet().size());
        assertTrue(processResult.getRemarkSet().iterator().next() instanceof ProcessRemark);
        assertEquals(RemarkMessageStore.H1_TAG_MISSING_MSG, 
                processResult.getRemarkSet().iterator().next().getMessageCode());
        

        //----------------------------------------------------------------------
        //------------------------------2Failed-02------------------------------
        //----------------------------------------------------------------------
        processResult = processPageTest("Rgaa30.Test.09.01.01-2Failed-02");
        // check number of elements in the page
        assertEquals(0, processResult.getElementCounter());
        // check test result
        assertEquals(TestSolution.FAILED, processResult.getValue());
        // check number of remarks and their value
        assertEquals(1, processResult.getRemarkSet().size());
        assertTrue(processResult.getRemarkSet().iterator().next() instanceof ProcessRemark);
        assertEquals(RemarkMessageStore.H1_TAG_MISSING_MSG, 
                processResult.getRemarkSet().iterator().next().getMessageCode());
        

        //----------------------------------------------------------------------
        //------------------------------2Failed-A01------------------------------
        //----------------------------------------------------------------------
        processResult = processPageTest("Rgaa30.Test.09.01.01-2Failed-A01");
        // check number of elements in the page
        assertEquals(0, processResult.getElementCounter());
        // check test result
        assertEquals(TestSolution.FAILED, processResult.getValue());
        // check number of remarks and their value
        assertEquals(1, processResult.getRemarkSet().size());
        assertTrue(processResult.getRemarkSet().iterator().next() instanceof ProcessRemark);
        assertEquals(RemarkMessageStore.H1_TAG_MISSING_MSG, 
                processResult.getRemarkSet().iterator().next().getMessageCode());
    }

    
    @Override
    protected void setConsolidate() {
        assertEquals(TestSolution.PASSED,
                consolidate("Rgaa30.Test.09.01.01-1Passed-01").getValue());
        assertEquals(TestSolution.PASSED,
                consolidate("Rgaa30.Test.09.01.01-1Passed-02").getValue());
        assertEquals(TestSolution.PASSED,
                consolidate("Rgaa30.Test.09.01.01-1Passed-A01").getValue());
        assertEquals(TestSolution.FAILED,
                consolidate("Rgaa30.Test.09.01.01-2Failed-01").getValue());
        assertEquals(TestSolution.FAILED,
                consolidate("Rgaa30.Test.09.01.01-2Failed-02").getValue());
        assertEquals(TestSolution.FAILED,
                consolidate("Rgaa30.Test.09.01.01-2Failed-A01").getValue());
    }

}
