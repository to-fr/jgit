/*
 * Copyright (C) 2021, Thomas Wolf <thomas.wolf@paranor.ch> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.transport.sshd.agent.Connector;
import org.eclipse.jgit.transport.sshd.agent.ConnectorFactory;
import org.eclipse.jgit.util.SystemReader;

/**
 * An {@link ConnectorFactory} for connecting to an OpenSSH SSH agent.
 */
public class Factory implements ConnectorFactory {

	private static final String NAME = "jgit-builtin"; //$NON-NLS-1$

	@Override
	public Connector create(String identityAgent, File homeDir)
			throws IOException {
		if (SystemReader.getInstance().isWindows()) {
			return new PageantConnector();
		}
		return new UnixDomainSocketConnector(identityAgent);
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
