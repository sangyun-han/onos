/*
 * Copyright 2017-present Open Networking Laboratory
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
package org.onosproject.incubator.net.virtual.impl.provider;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.incubator.net.virtual.*;
import org.onosproject.incubator.net.virtual.provider.AbstractVirtualProvider;
import org.onosproject.incubator.net.virtual.provider.VirtualDeviceProvider;
import org.onosproject.incubator.net.virtual.provider.VirtualProviderRegistryService;
import org.onosproject.net.*;
import org.onosproject.net.device.DeviceEvent;
import org.onosproject.net.device.DeviceListener;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.provider.ProviderId;
import org.onosproject.net.topology.*;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of virtual network device provider.
 */
@Component(immediate = true)
@Service
public class DefaultVirtualDeviceProvider extends AbstractVirtualProvider
        implements VirtualDeviceProvider{

    private final Logger Log = getLogger(getClass());

    private static final String NETWORK_NULL = "Network ID cannot be null";
    private static final String DEVICE_NULL = "Device ID cannot be null";

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected DeviceService deviceService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected TopologyService topologyService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected VirtualProviderRegistryService providerRegistryService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected VirtualNetworkAdminService virtualNetworkAdminService;

    private ApplicationId appId;
    private DeviceListener deviceListener;

    /**
     * Creates a provider with the supplied identifier.
     */
    protected DefaultVirtualDeviceProvider() {
        super(new ProviderId("vnet-device",
                "org.onosproject.virtual.vnet-device"));
    }

    @Activate
    public void activate() {
        appId = coreService.registerApplication(
                "org.onosproject.virtual.vnet-device");

        providerRegistryService.registerProvider(this);

        Log.info("Started");
    }

    @Deactivate
    public void deactivate() {
        providerRegistryService.unregisterProvider(this);
        Log.info("Stopped");
    }

    @Modified
    protected void modified(ComponentContext context) {
        Dictionary<?, ?> properties = context.getProperties();
    }

    @Override
    public void roleChanged(NetworkId networkId, DeviceId deviceId, MastershipRole newRole) {
        checkNotNull(deviceId, DEVICE_NULL);
        // deviceId : controller.setRole(dpid(deviceId.uri()), RoleState.MASTER);
        switch (newRole) {
            case STANDBY:
                break;
            case MASTER:
                break;
            case NONE:
                break;
            default:
                Log.error("Wrong Mastership state");
        }
        Log.debug("Accepting mastership role change to {} for device {}",
                newRole, deviceId);
    }

    @Override
    public boolean isTraversable(NetworkId networkId, ConnectPoint src, ConnectPoint dst) {
        final boolean[] foundSrc = new boolean[1];
        final boolean[] foundDst = new boolean[1];

        Topology topology = topologyService.currentTopology();
        Set<Path> paths = topologyService.getPaths(topology, src.deviceId(),
                dst.deviceId());

        paths.forEach(path -> {
            foundSrc[0] = false;
            foundDst[0] = false;

            path.links().forEach(link -> {
                if (link.src().equals(src)) {
                    foundSrc[0] = true;
                }
                if (link.dst().equals(dst)) {
                    foundDst[0] = true;
                }
            });
            if (foundSrc[0] && foundDst[0]) {
                return;
            }
        });

        return foundSrc[0] && foundDst[0];
    }

    @Override
    public boolean isReachable(NetworkId networkId, DeviceId deviceId) {
        checkNotNull(deviceId, DEVICE_NULL);
        checkNotNull(deviceId, "");

        Set = virtualNetworkAdminService.getPhysicalDevices(networkId, deviceId);


        return false;
    }

    @Override
    public void changePortState(NetworkId networkId, DeviceId deviceId,
                                PortNumber portNumber, boolean enable) {
        checkNotNull(deviceId, DEVICE_NULL);
/**
 *
 *         final Dpid dpid = dpid(deviceId.uri());
 OpenFlowSwitch sw = controller.getSwitch(dpid);
 if (sw == null || !sw.isConnected()) {
 LOG.error("Failed to change portState on device {}", deviceId);
 return;
 }
 OFPortMod.Builder pmb = sw.factory().buildPortMod();
 OFPort port = OFPort.of((int) portNumber.toLong());
 pmb.setPortNo(port);
 if (enable) {
 pmb.setConfig(0x0); // port_down bit 0
 } else {
 pmb.setConfig(0x1); // port_down bit 1
 }
 pmb.setMask(0x1);
 pmb.setAdvertise(0x0);
 for (OFPortDesc pd : sw.getPorts()) {
 if (pd.getPortNo().equals(port)) {
 pmb.setHwAddr(pd.getHwAddr());
 break;
 }
 }
 sw.sendMsg(Collections.singletonList(pmb.build()));
 */
    }

    /**
     * Translates the requested physical devices into virtual device.
     * The translation could be N:1 or 1:1.
     *
     * @param deviceIds a collection of physical devices
     * @return
     */
    private VirtualDevice virtualize(DeviceId... deviceIds) {
        checkNotNull(deviceIds, DEVICE_NULL);
        for(DeviceId d : deviceIds) {

        }

        return null;
    }

    private VirtualDevice virtualize(DeviceId deviceId) {

        return null;
    }

    /**
     * Translates the requested virtual device into physical devices by using
     * the virtual device's identifier.
     * The translation could be 1:N or 1:1.
     *
     * @param networkId network identifier
     * @param virtualDeviceId the requested virtual device identifier
     * @return a set of physical device about the requested virtual device id
     */
    private Set<DeviceId> devirtualize(NetworkId networkId,
                                       DeviceId virtualDeviceId) {
        checkNotNull(networkId, NETWORK_NULL);
        checkNotNull(virtualDeviceId, DEVICE_NULL);


        //For checking the existence of the virtual device
        Set<VirtualDevice> virtualDevices =
                virtualNetworkAdminService.getVirtualDevices(networkId);
        Optional<VirtualDevice> virtualDevice = virtualDevices.
                stream().filter(v -> v.id() == virtualDeviceId).findAny();

        if (!virtualDevice.isPresent()) {
            Log.info("Virtual Device {} doesn't exist in Network {}",
                    virtualDeviceId, networkId);
            return null;
        }

        Set<DeviceId> physicalDevices = virtualNetworkAdminService.
                getPhysicalDevices(networkId, virtualDevice.get().id());

        if (physicalDevices.isEmpty()) {
            Log.info("Virtual Device {} doesn't bind any ports in Network {}",
                    virtualDeviceId, networkId);
        }

        return physicalDevices;
    }

    /**
     * Translates the requested virtual device into physical devices.
     * The translation could be 1:N or 1:1.
     *
     * @param networkId network identifier
     * @param virtualDevice the requested virtual device
     * @return a set of physical device about the reqeusted virtual device
     */
    private Set<DeviceId> devirtualize(NetworkId networkId,
                                       VirtualDevice virtualDevice) {
        checkNotNull(networkId, NETWORK_NULL);
        Set<DeviceId> physicalDevices = virtualNetworkAdminService.
                getPhysicalDevices(networkId, virtualDevice.id());

        return physicalDevices;
    }

    private class InternalDeviceListener implements DeviceListener {
        @Override
        public void event(DeviceEvent event) {

        }
    }
    private class InternalVirtualDeviceManager {

    }
}
