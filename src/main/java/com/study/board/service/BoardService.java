package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepositoty boardRepositoty;

    public void write(Board board, MultipartFile file) throws Exception{ //써진 글을 가져와서 repositoty에 저장 => 데이터 베이스에 들어감
                                                        //Exception으로 예외처리
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files"; //프로젝트 경로를 담아오게됨

        UUID uuid = UUID.randomUUID(); //식별자로 인해서 랜덤으로 이름을 만들어 줌

        String fileName = uuid + "_" + file.getOriginalFilename(); //랜덤이름을 파일 이름 앞에 붙임

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile); //저장할 경로 지정 후에 File 클래스로 들어온 file을 넣을 껍데기를 지정을 하고

        boardRepositoty.save(board);
    }

    public List<Board> boardList() { //게시글 리스트 처리

        return boardRepositoty.findAll();
    }


    public Board boardView(Integer id) { //특정 게시글 불러오기

        return boardRepositoty.findById(id).get(); //get()으로 반환
    }


    public void boardDelete(Integer id){ //특정 게시글 삭제

        boardRepositoty.deleteById(id);
    }
}
