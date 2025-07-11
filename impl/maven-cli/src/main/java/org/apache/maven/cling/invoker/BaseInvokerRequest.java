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
package org.apache.maven.cling.invoker;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.maven.api.annotations.Nonnull;
import org.apache.maven.api.annotations.Nullable;
import org.apache.maven.api.cli.CoreExtensions;
import org.apache.maven.api.cli.InvokerRequest;
import org.apache.maven.api.cli.Options;
import org.apache.maven.api.cli.ParserRequest;
import org.apache.maven.api.cli.cisupport.CIInfo;

import static java.util.Objects.requireNonNull;

public class BaseInvokerRequest implements InvokerRequest {
    private final ParserRequest parserRequest;
    private final boolean parsingFailed;
    private final Path cwd;
    private final Path installationDirectory;
    private final Path userHomeDirectory;
    private final Map<String, String> userProperties;
    private final Map<String, String> systemProperties;
    private final Path topDirectory;
    private final Path rootDirectory;
    private final List<CoreExtensions> coreExtensions;
    private final CIInfo ciInfo;
    private final Options options;

    @SuppressWarnings("ParameterNumber")
    public BaseInvokerRequest(
            @Nonnull ParserRequest parserRequest,
            boolean parsingFailed,
            @Nonnull Path cwd,
            @Nonnull Path installationDirectory,
            @Nonnull Path userHomeDirectory,
            @Nonnull Map<String, String> userProperties,
            @Nonnull Map<String, String> systemProperties,
            @Nonnull Path topDirectory,
            @Nullable Path rootDirectory,
            @Nullable List<CoreExtensions> coreExtensions,
            @Nullable CIInfo ciInfo,
            @Nullable Options options) {
        this.parserRequest = requireNonNull(parserRequest);
        this.parsingFailed = parsingFailed;
        this.cwd = requireNonNull(cwd);
        this.installationDirectory = requireNonNull(installationDirectory);
        this.userHomeDirectory = requireNonNull(userHomeDirectory);

        this.userProperties = requireNonNull(userProperties);
        this.systemProperties = requireNonNull(systemProperties);
        this.topDirectory = requireNonNull(topDirectory);
        this.rootDirectory = rootDirectory;
        this.coreExtensions = coreExtensions;
        this.ciInfo = ciInfo;
        this.options = options;
    }

    @Override
    public ParserRequest parserRequest() {
        return parserRequest;
    }

    @Override
    public boolean parsingFailed() {
        return parsingFailed;
    }

    @Override
    public Path cwd() {
        return cwd;
    }

    @Override
    public Path installationDirectory() {
        return installationDirectory;
    }

    @Override
    public Path userHomeDirectory() {
        return userHomeDirectory;
    }

    @Override
    public Map<String, String> userProperties() {
        return userProperties;
    }

    @Override
    public Map<String, String> systemProperties() {
        return systemProperties;
    }

    @Override
    public Path topDirectory() {
        return topDirectory;
    }

    @Override
    public Optional<Path> rootDirectory() {
        return Optional.ofNullable(rootDirectory);
    }

    @Override
    public Optional<List<CoreExtensions>> coreExtensions() {
        return Optional.ofNullable(coreExtensions);
    }

    @Override
    public Optional<CIInfo> ciInfo() {
        return Optional.ofNullable(ciInfo);
    }

    public Optional<Options> options() {
        return Optional.ofNullable(options);
    }
}
