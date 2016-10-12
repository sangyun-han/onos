/*
 * Copyright 2016-present Open Networking Laboratory
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

package org.onosproject.incubator.net.virtual.provider;

import org.onosproject.net.DeviceId;
import org.onosproject.net.MastershipRole;
import org.onosproject.net.PortNumber;
import org.onosproject.net.provider.Provider;

/**
 * Abstraction of a virtual device information provider.
 */
public interface VirtualDeviceProvider extends Provider {

    /**
     * Notifies the provider of a mastership role change for the specified
     * device as decided by the core.
     *
     * @param deviceId  device identifier
     * @param newRole newly determined mastership role
     */
    void roleChanged(DeviceId deviceId, MastershipRole newRole);

    /**
     * Indicates whether or not the specified device is traversable/reachable.
     *
     * @param deviceId  device identifier
     * @return true if the device is traversable/reachable from this provider
     */
    boolean isTraversable(DeviceId deviceId);



    /**
     * Administratively enables or disables a port.
     *
     * @param deviceId device identifier
     * @param portNumber port number
     * @param enable true if port is to be enabled, false to disable
     */
    void changePortState(DeviceId deviceId, PortNumber portNumber,
                         boolean enable);
}
