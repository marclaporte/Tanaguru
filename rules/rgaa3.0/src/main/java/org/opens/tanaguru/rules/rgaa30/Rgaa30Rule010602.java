/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2015  Tanaguru.org
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
 * Contact us by mail: tanaguru AT tanaguru DOT org
 */
package org.opens.tanaguru.rules.rgaa30;

import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.ruleimplementation.AbstractMarkerPageRuleImplementation;
import org.opens.tanaguru.rules.elementchecker.element.ElementPresenceChecker;
import org.opens.tanaguru.rules.elementselector.ImageElementSelector;
import static org.opens.tanaguru.rules.keystore.AttributeStore.DATA_ATTR;
import static org.opens.tanaguru.rules.keystore.CssLikeQueryStore.OBJECT_TYPE_IMG_NOT_IN_LINK_CSS_LIKE_QUERY;
import static org.opens.tanaguru.rules.keystore.HtmlElementStore.TEXT_ELEMENT2;
import static org.opens.tanaguru.rules.keystore.MarkerStore.DECORATIVE_IMAGE_MARKER;
import static org.opens.tanaguru.rules.keystore.MarkerStore.INFORMATIVE_IMAGE_MARKER;
import static org.opens.tanaguru.rules.keystore.RemarkMessageStore.CHECK_DETAILED_DESC_DEFINITION_OF_INFORMATIVE_IMG_MSG;
import static org.opens.tanaguru.rules.keystore.RemarkMessageStore.CHECK_NATURE_OF_IMAGE_AND_DETAILED_DESC_AVAILABILITY_MSG;

/**
 * Implementation of the rule 1.6.2 of the referential Rgaa 3.0.
 * <br/>
 * For more details about the implementation, refer to <a href="https://github.com/Tanaguru/Tanaguru-rules-RGAA-3-doc/wiki/Rule-1-6-2">the rule 1.6.2 design page.</a>
 * @see <a href="https://references.modernisation.gouv.fr/sites/default/files/RGAA3/referentiel_technique.htm#test-1-6-2"> 1.6.2 rule specification</a>
 *
 */
public class Rgaa30Rule010602 extends AbstractMarkerPageRuleImplementation {

    /**
     * Default constructor
     */
    public Rgaa30Rule010602() {
        super(
                new ImageElementSelector(OBJECT_TYPE_IMG_NOT_IN_LINK_CSS_LIKE_QUERY),

                // the informative images are part of the scope
                INFORMATIVE_IMAGE_MARKER, 

                // the decorative images are not part of the scope
                DECORATIVE_IMAGE_MARKER, 

                // checker for elements identified by marker
                new ElementPresenceChecker(
                    // solution when at least one element is found
                    TestSolution.NEED_MORE_INFO,
                    // solution when no element is found
                    TestSolution.NOT_APPLICABLE,
                    // manual check message
                    CHECK_DETAILED_DESC_DEFINITION_OF_INFORMATIVE_IMG_MSG,
                    null, 
                    // evidence elements
                    TEXT_ELEMENT2,
                    DATA_ATTR), 
                
                // checker for elements not identified by marker
                new ElementPresenceChecker(
                    // solution when at least one element is found
                    TestSolution.NEED_MORE_INFO,
                    // solution when no element is found
                    TestSolution.NOT_APPLICABLE,
                    // manual check message
                    CHECK_NATURE_OF_IMAGE_AND_DETAILED_DESC_AVAILABILITY_MSG,
                    null, 
                    // evidence elements
                    TEXT_ELEMENT2,
                    DATA_ATTR)
            );
    }

}
