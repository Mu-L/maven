/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.it;

import java.io.File;

import org.junit.jupiter.api.Test;

public class MavenITmng5958LifecyclePhaseBinaryCompat extends AbstractMavenIntegrationTestCase {

    public MavenITmng5958LifecyclePhaseBinaryCompat() {
        super("(3.3.9,)");
    }

    @Test
    public void testGood() throws Exception {
        File testDir = extractResources("/mng-5958-lifecycle-phases");

        // First, build the test extension
        Verifier verifier = newVerifier(new File(testDir, "mng5958-extension").getAbsolutePath());
        verifier.setAutoclean(false);
        verifier.deleteDirectory("target");
        verifier.addCliArgument("install");
        verifier.execute();
        verifier.verifyErrorFreeLog();

        // Then, build the test plugin
        verifier = newVerifier(new File(testDir, "mng5958-plugin").getAbsolutePath());
        verifier.setAutoclean(false);
        verifier.deleteDirectory("target");
        verifier.addCliArgument("install");
        verifier.execute();
        verifier.verifyErrorFreeLog();

        // Finally, run the good test project
        verifier = newVerifier(new File(testDir, "good").getAbsolutePath());
        verifier.addCliArgument("validate");
        verifier.execute();
        verifier.verifyErrorFreeLog();
        verifier.verifyTextInLog("CLASS_NAME=java.lang.String");
    }

    @Test
    public void testBad() throws Exception {
        File testDir = extractResources("/mng-5958-lifecycle-phases");

        // Extension and plugin are already built by testGood, but let's ensure they're available
        // Build the test extension
        Verifier verifier = newVerifier(new File(testDir, "mng5958-extension").getAbsolutePath());
        verifier.setAutoclean(false);
        verifier.addCliArgument("install");
        verifier.execute();
        verifier.verifyErrorFreeLog();

        // Build the test plugin
        verifier = newVerifier(new File(testDir, "mng5958-plugin").getAbsolutePath());
        verifier.setAutoclean(false);
        verifier.addCliArgument("install");
        verifier.execute();
        verifier.verifyErrorFreeLog();

        // Run the bad test project
        verifier = newVerifier(new File(testDir, "bad").getAbsolutePath());
        try {
            verifier.addCliArgument("validate");
            verifier.execute();
        } catch (VerificationException e) {
            // There is no good way to test for Java 9+ in Verifier in order to add 'java.base/' to the string.
            // Java 11: Internal error: java.lang.ClassCastException: class org.apache.maven..
            verifier.verifyTextInLog("[ERROR] Internal error: java.lang.ClassCastException: ");
            verifier.verifyTextInLog("org.apache.maven.lifecycle.mapping.LifecyclePhase cannot be cast to ");
        }
    }
}
