/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.dmn.api.definition.v1_1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.dmn.api.property.dmn.Id;
import org.kie.workbench.common.dmn.api.property.dmn.Text;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class InputClauseUnaryTestsTest {

    @Test
    public void testDefaultConstraintTypeProperty() {
        final InputClauseUnaryTests inputClause = new InputClauseUnaryTests();
        assertEquals("", inputClause.getConstraintTypeProperty().getValue());
    }

    @Test
    public void ensureThatConstraintTypePropertyIsSetToExpression() {
        ensureThatConstraintTypePropertyIsSet(ConstraintType.EXPRESSION);
    }

    @Test
    public void ensureThatConstraintTypePropertyIsSetToRange() {
        ensureThatConstraintTypePropertyIsSet(ConstraintType.RANGE);
    }

    @Test
    public void ensureThatConstraintTypePropertyIsSetToEnumeration() {
        ensureThatConstraintTypePropertyIsSet(ConstraintType.ENUMERATION);
    }

    private void ensureThatConstraintTypePropertyIsSet(final ConstraintType constraintType) {
        final InputClauseUnaryTests inputClause = new InputClauseUnaryTests(new Id("id"),
                                                                            new Text(""),
                                                                            constraintType);

        assertEquals(constraintType.value(), inputClause.getConstraintTypeProperty().getValue());
    }

    @Test
    public void ensureThatConstraintTypeIsSetToExpression() {
        ensureThatConstraintTypeIsSet(ConstraintType.EXPRESSION);
    }

    @Test
    public void ensureThatConstraintTypeIsSetToRange() {
        ensureThatConstraintTypeIsSet(ConstraintType.RANGE);
    }

    @Test
    public void ensureThatConstraintTypeIsSetToEnumeration() {
        ensureThatConstraintTypeIsSet(ConstraintType.ENUMERATION);
    }

    @Test
    public void ensureThatConstraintTypeIsSetToNull() {
        ensureThatConstraintTypeIsSet(null);
    }

    private void ensureThatConstraintTypeIsSet(final ConstraintType constraintType) {
        final InputClauseUnaryTests inputClause = new InputClauseUnaryTests(new Id("id"),
                                                                            new Text(""),
                                                                            constraintType);

        assertEquals(constraintType, inputClause.getConstraintType());
    }
}