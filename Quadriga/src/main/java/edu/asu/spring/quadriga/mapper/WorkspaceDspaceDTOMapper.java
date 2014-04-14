package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.factories.IBitStreamFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DspaceKeysFactory;
import edu.asu.spring.quadriga.domain.impl.dspace.BitStream;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;

@Service
public class WorkspaceDspaceDTOMapper {
	
	@Autowired
	private DspaceKeysFactory dsapceKeysFactory;

	@Autowired
	private IBitStreamFactory bitstreamFactory;
	
	public IBitStream getBitstream(WorkspaceDspaceDTO workspaceDspaceDTO)
	{
		IBitStream bitstream = new BitStream();
		if(workspaceDspaceDTO != null)
		{
			bitstream = bitstreamFactory.createBitStreamObject();					
			bitstream.setId(workspaceDspaceDTO.getWorkspaceDspaceDTOPK().getBitstreamid());
			bitstream.setItemHandle(workspaceDspaceDTO.getWorkspaceDspaceDTOPK().getItemHandle());
		}
		return bitstream;
	}
	
	
	public List<IBitStream> getBitstreamList(List<WorkspaceDspaceDTO> workspaceDspaceDTOList)
	{
		Iterator<WorkspaceDspaceDTO> workspaceDspaceItr = workspaceDspaceDTOList.listIterator();
		List<IBitStream> bitstreamList = new ArrayList<IBitStream>();
		while(workspaceDspaceItr.hasNext())
		{
			WorkspaceDspaceDTO workspaceDspaceDTO = workspaceDspaceItr.next();
			bitstreamList.add(getBitstream(workspaceDspaceDTO));
		}
		return bitstreamList;
	}
}
