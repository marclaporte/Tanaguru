/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2014  Open-S Company
 *
 * This program is free software: you can redistribute it and/or modify
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
 * Contact us by mail: open-s AT open-s DOT com
 */

package org.opens.tanaguru.rules.rgaa30;

import org.jsoup.nodes.Element;
import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.processor.SSPHandler;
import org.opens.tanaguru.ruleimplementation.AbstractPageRuleMarkupImplementation;
import org.opens.tanaguru.ruleimplementation.ElementHandler;
import org.opens.tanaguru.ruleimplementation.TestSolutionHandler;
import org.opens.tanaguru.rules.elementchecker.lang.LangChecker;
import org.opens.tanaguru.rules.elementchecker.lang.LangDeclarationValidityChecker;
import org.opens.tanaguru.rules.elementselector.ElementSelector;
import org.opens.tanaguru.rules.elementselector.SimpleElementSelector;
import static org.opens.tanaguru.rules.keystore.CssLikeQueryStore.ELEMENT_WITH_LANG_ATTR_CSS_LIKE_QUERY;

/**
 * Implementation of the rule 8.8.2 of the referential Rgaa 3.0.
 * <br/>
 * For more details about the implementation, refer to <a href="https://github.com/Tanaguru/Tanaguru-rules-RGAA-3-doc/wiki/Rule-8-8-2">the rule 8.8.2 design page.</a>
 * @see <a href="https://references.modernisation.gouv.fr/sites/default/files/RGAA3/referentiel_technique.htm#test-8-8-2"> 8.8.2 rule specification</a>
 *
 */
public class Rgaa30Rule080802 extends AbstractPageRuleMarkupImplementation {

    /**
     * Default constructor
     */
    public Rgaa30Rule080802 () {
        super();
    }
    
    @Override
    protected void select(SSPHandler sspHandler, ElementHandler<Element> elementHandler) {
        ElementSelector selector = new SimpleElementSelector(ELEMENT_WITH_LANG_ATTR_CSS_LIKE_QUERY);
        selector.selectElements(sspHandler, elementHandler);
   }

    @Override
    protected void check(
            SSPHandler sspHandler, 
            ElementHandler<Element> elementHandler, 
            TestSolutionHandler testSolutionHandler) {
        if (elementHandler.isEmpty()) {
            testSolutionHandler.addTestSolution(TestSolution.NOT_APPLICABLE);
            return;
        }
        LangChecker ec = new LangDeclarationValidityChecker(false, true);
        ec.setNomenclatureLoaderService(nomenclatureLoaderService);
        ec.check(sspHandler, elementHandler, testSolutionHandler);
    }

}