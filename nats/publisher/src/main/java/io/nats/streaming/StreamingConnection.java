// Copyright 2015-2018 The NATS Authors
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.nats.streaming;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * A {@code StreamingConnection} object is a client's active connection to the STAN streaming
 * data system.
 */
public interface StreamingConnection extends AutoCloseable {
    /**
     * Publishes the payload specified by {@code data} to the subject specified by {@code subject},
     * and blocks until an ACK or error is returned.
     *
     * @param subject the subject to which the message is to be published
     * @param data    the message payload
     * @throws IOException           if the publish operation is not successful
     * @throws InterruptedException  if the calling thread is interrupted before the call completes
     * @throws IllegalStateException if the connection is closed
     */
    void publish(String subject, byte[] data) throws IOException, InterruptedException;

    /**
     * Publishes the payload specified by {@code data} to the subject specified by {@code subject}
     * and asynchronously processes the ACK or error state via the supplied {@link AckHandler}
     *
     * @param subject the subject to which the message is to be published
     * @param data    the message payload
     * @param ah      the {@link AckHandler} to invoke when an ack is received, passing the
     *                message GUID
     *                and any error information.
     * @return the message GUID
     * @throws IOException           if an I/O exception is encountered
     * @throws InterruptedException  if the calling thread is interrupted before the call completes
     * @throws IllegalStateException if the connection is closed
     * @see AckHandler
     */
    String publish(String subject, byte[] data, AckHandler ah)
            throws IOException, InterruptedException;

    /**
     * Creates a {@link Subscription} with interest in a given subject, assigns the callback, and
     * immediately starts receiving messages.
     *
     * @param subject the subject of interest
     * @param cb      a {@code MessageHandler} callback used to process messages received by the
     *                {@code Subscription}
     * @return the {@code Subscription} object, or null if the subscription request timed out
     * @throws IOException          if an I/O exception is encountered
     * @throws InterruptedException if the calling thread is interrupted before the call completes
     * @see MessageHandler
     * @see Subscription
     */
    Subscription subscribe(String subject, MessageHandler cb)
            throws IOException, InterruptedException;

    /**
     * Creates a {@link Subscription} with interest in a given subject using the given
     * {@link SubscriptionOptions}, assigns the callback, and immediately starts receiving messages.
     *
     * @param subject the subject of interest
     * @param cb      a {@link MessageHandler} callback used to process messages received by the
     *                {@link Subscription}
     * @param opts    the {@link SubscriptionOptions} to configure this {@link Subscription}
     * @return the {@link Subscription} object, or null if the subscription request timed out
     * @throws IOException          if an I/O exception is encountered
     * @throws InterruptedException if the calling thread is interrupted before the call completes
     * @see MessageHandler
     * @see Subscription
     * @see SubscriptionOptions
     */
    Subscription subscribe(String subject, MessageHandler cb, SubscriptionOptions opts)
            throws IOException, InterruptedException;

    /**
     * Creates a {@code Subscription} in the queue group specified by {@code queue} with interest in
     * a given subject, assigns the message callback, and immediately starts receiving messages.
     *
     * @param subject the subject of interest
     * @param queue   optional queue group
     * @param cb      a {@link MessageHandler} callback used to process messages received by the
     *                {@link Subscription}
     * @return the {@link Subscription} object, or null if the subscription request timed out
     * @throws IOException          if an I/O exception is encountered
     * @throws InterruptedException if the calling thread is interrupted before the call completes
     */
    Subscription subscribe(String subject, String queue, MessageHandler cb)
            throws IOException, InterruptedException;

    /**
     * Creates a {@code Subscription} in the queue group specified by {@code queue} with interest in
     * a given subject, assigns the message callback, and immediately starts receiving messages.
     *
     * @param subject the subject of interest
     * @param queue   optional queue group
     * @param cb      a {@link MessageHandler} callback used to process messages received by the
     *                {@link Subscription}
     * @param opts    the {@link SubscriptionOptions} to configure for this {@link Subscription}
     * @return the {@code Subscription} object, or null if the subscription request timed out
     * @throws IOException          if an I/O exception is encountered
     * @throws InterruptedException if the calling thread is interrupted before the call completes
     */
    Subscription subscribe(String subject, String queue, MessageHandler cb,
                           SubscriptionOptions opts) throws IOException, InterruptedException;

    /**
     * Returns the underlying NATS connection. Use with caution, especially if you didn't create the
     * connection.
     *
     * @return the underlying NATS connection.
     * @see io.nats.client.Connection
     */
    io.nats.client.Connection getNatsConnection();

    /**
     * Closes the connection.
     *
     * @throws IOException      if an error occurs
     * @throws TimeoutException if the close request is not responded to within the timeout period.
     */
    void close() throws IOException, TimeoutException, InterruptedException;
}
