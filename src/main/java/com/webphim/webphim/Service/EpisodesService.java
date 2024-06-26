package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Episodes;
import com.webphim.webphim.Reponsitory.EpisodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodesService {
    @Autowired
    private EpisodesRepository episodesRepository;
    public Episodes saveEpisode(Episodes episode) {
        return episodesRepository.save(episode);
    }

    public Episodes updateEpisode(Long episodeId, Episodes updatedEpisode) {
        Optional<Episodes> optionalEpisode = episodesRepository.findById(episodeId);
        if (optionalEpisode.isPresent()) {
            Episodes existingEpisode = optionalEpisode.get();
            existingEpisode.setName(updatedEpisode.getName());
            existingEpisode.setSlug(updatedEpisode.getSlug());
            existingEpisode.setContentEpisode(updatedEpisode.getContentEpisode());
            existingEpisode.setLinkEmbed(updatedEpisode.getLinkEmbed());
            existingEpisode.setLinkM3u8(updatedEpisode.getLinkM3u8());
            return episodesRepository.save(existingEpisode);
        }
        return null; // Handle appropriately if episodeId does not exist
    }

    public void deleteEpisode(Long episodeId) {
        episodesRepository.deleteById(episodeId);
    }

    public Episodes getEpisodeById(Long episodeId) {
        Optional<Episodes> optionalEpisode = episodesRepository.findById(episodeId);
        return optionalEpisode.orElse(null); // Return null or handle not found case
    }


}
