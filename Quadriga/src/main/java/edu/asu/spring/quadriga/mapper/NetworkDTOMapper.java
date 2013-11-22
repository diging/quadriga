package edu.asu.spring.quadriga.mapper;

import java.util.Date;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.Network;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.web.network.INetworkStatus;


@Service
public class NetworkDTOMapper {

	public NetworksDTO getNetworksDTO(String networkid, String networkName, IUser user, String workspaceid)
	{
		NetworksDTO networkDTO = new NetworksDTO(networkid, workspaceid, networkName, user.getName(), INetworkStatus.PENDING, user.getName(), new Date(), user.getName(), new Date());
		return networkDTO;
	}
}
