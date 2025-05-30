// SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
//
// SPDX-License-Identifier: Apache-2.0
package org.eclipse.lmos.arc.agents.examples.openai

import kotlinx.coroutines.runBlocking
import org.eclipse.lmos.arc.agents.agent.ask
import org.eclipse.lmos.arc.agents.agents
import org.eclipse.lmos.arc.agents.getChatAgent
import org.eclipse.lmos.arc.core.getOrNull

/**
 * Demonstrates how to create a simple Agent and function.
 *
 * Setup:
 *  - Set the OpenAI API key as a System property or environment variable.
 *
 * Dependencies:
 *  - implementation(project(":arc-agents"))
 *  - implementation(project(":arc-azure-client"))
 */
fun main(): Unit = runBlocking {
    // Set the OpenAI API as a System property or environment variable.
    // System.setProperty("OPENAI_API_KEY", "****")

    val agents = agents(
        functions = {
            function(
                name = "get_weather",
                description = "Get the weather for a given location.",
            ) {
                "THe weather is sunny"
            }
        },
    ) {
        agent {
            name = "MyAgent"
            model { "gpt-4o" }
            tools { +"get_weather" }
            prompt {
                """
                You are a weather assistant. Help the user with their questions about the weather.
                """
            }
        }
    }

    val reply = agents.getChatAgent("MyAgent").ask("What is the weather like today?").getOrNull()
    println(reply)
}
