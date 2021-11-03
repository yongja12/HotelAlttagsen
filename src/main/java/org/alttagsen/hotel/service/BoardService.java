package org.alttagsen.hotel.service;

import org.alttagsen.hotel.dto.BoardDTO;
import org.alttagsen.hotel.dto.PageRequestDTO;
import org.alttagsen.hotel.dto.PageResultDTO;
import org.alttagsen.hotel.entity.Board;
import org.alttagsen.hotel.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);
    
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO); // 목록 처리

    BoardDTO get(Long bno); // 게시물 조회 처리

    void removeWithReplies(Long bno); // 댓글 삭제 처리

    void modify(BoardDTO boardDTO); // 게시물 수정 처리
    
    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().userId(dto.getWriterUserId()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerUserId(member.getUserId())
                .writerUserName(member.getUserName())
                .replyCount(replyCount.intValue()) // long으로 나오므로 int로 처리하도록
                .build();
        return boardDTO;
    }
}
