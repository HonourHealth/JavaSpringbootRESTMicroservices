package com.example.user.service.handler;

import com.example.user.UserInformation;
import com.example.user.UserInformationRequest;
import com.example.user.entity.PortfolioItem;
import com.example.user.entity.User;
import com.example.user.exceptions.UnknownUserException;
import com.example.user.repository.PortfolioItemRepository;
import com.example.user.repository.UserRepository;
import com.example.user.util.EntityMessageMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserInformationRequestHandler {
    private final UserRepository userRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public UserInformation getUserInformation(UserInformationRequest userInformationRequest) {
        User user = this.userRepository.findById(userInformationRequest.getUserId())
                .orElseThrow(() -> new UnknownUserException(userInformationRequest.getUserId()));
        List<PortfolioItem> portfolioItems = this.portfolioItemRepository.findAllByUserId(userInformationRequest.getUserId());
        return EntityMessageMapper.toUserInformation(user, portfolioItems);
    }
}
