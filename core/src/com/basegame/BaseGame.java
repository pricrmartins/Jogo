package com.basegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class BaseGame extends ApplicationAdapter {

    Carro carroPersonagem;
    Carro carroFigurante1;
    Carro carroFigurante2;
    Carro carroFigurante3;
    Carro carroFigurante4;

    Texture imagemCarroPrincipal;
    Texture imagemCarroFigurante;

    SpriteBatch batch;
    SpriteBatch batch2;

    OrthographicCamera camera;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    BitmapFont fonte;
    int ACELERACAO = 1;
    Vector2 posicao;
    Vector2 velocidade = new Vector2(0, 0);
    int xIniciao;
    int yIniciao;
    int mapWidth = 0;
    int mapHeight = 0;
    int mapTileWidth = 0;
    int mapTileHeight = 0;
    int mapaLargura = 0;
    int mapaAltura = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        batch2 = new SpriteBatch();

        imagemCarroPrincipal = new Texture("carro1.png");
        imagemCarroFigurante = new Texture("carro2.png");

        carroFigurante1 = new Carro(imagemCarroFigurante, 0, 300);
        carroFigurante2 = new Carro(imagemCarroFigurante, 300, 0);
        carroFigurante3 = new Carro(imagemCarroFigurante, 300, 400);
        carroFigurante4 = new Carro(imagemCarroFigurante, 450, 300);

        // inicialização da câmera....
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        camera.position.x = (Gdx.graphics.getWidth() / 2) + (imagemCarroPrincipal.getWidth());
        camera.position.y = (Gdx.graphics.getHeight()) / 2 + (imagemCarroPrincipal.getHeight());
        xIniciao = (Gdx.graphics.getWidth() / 2);
        yIniciao = Gdx.graphics.getHeight() / 2;
        posicao = new Vector2(xIniciao, yIniciao);
        fonte = new BitmapFont();
        carroPersonagem = new Carro(imagemCarroPrincipal, xIniciao, yIniciao);
        // carregamento do cenário do jogo...
        tiledMap = new TmxMapLoader().load("untitled2.tmx");

        mapWidth = tiledMap.getProperties().get("width", Integer.class);
        mapHeight = tiledMap.getProperties().get("height", Integer.class);
        mapTileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        mapTileHeight = tiledMap.getProperties().get("tileheight", Integer.class);
        mapaLargura = mapWidth * mapTileWidth;
        mapaAltura = mapHeight * mapTileHeight;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocidade.y += ACELERACAO;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocidade.y -= ACELERACAO;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocidade.x -= ACELERACAO;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocidade.x += ACELERACAO;
        }

        if (velocidade.x > 0) {
            if (posicao.x + carroPersonagem.getImagem().getWidth() + velocidade.x > mapaLargura) {
                velocidade.x = 0;
            }
        } else if (velocidade.x < 0) {
            if (posicao.x + velocidade.x < 0) {
                velocidade.x = 0;
            }
        }

        if (velocidade.y > 0) {
            if (posicao.y + carroPersonagem.getImagem().getHeight() + velocidade.y > mapaAltura) {
                velocidade.y = 0;
            }
            if (posicao.y + carroPersonagem.getImagem().getHeight() + velocidade.y == mapaAltura) {
                velocidade.y = 0;
            }
        } else if (velocidade.y < 0) {
            if (posicao.y + velocidade.y < 0) {
                velocidade.y = 0;
            }
            if (posicao.y + carroPersonagem.getImagem().getHeight() + velocidade.y == 0) {
                velocidade.y = 0;
            }
        }

        // colisao figurante 1
        boolean colisao = (posicao.x + velocidade.x < carroFigurante1.getX() + carroFigurante1.getImagem().getWidth())
                && (posicao.x + carroPersonagem.getImagem().getWidth() + velocidade.x > carroFigurante1.getX())
                && (posicao.y + velocidade.y < carroFigurante1.getY() + carroFigurante1.getImagem().getHeight())
                && (posicao.y + carroPersonagem.getImagem().getHeight() + velocidade.y > carroFigurante1.getY());

        if (colisao) {
            velocidade.x = 0;
            velocidade.y = 0;
        }

        // colisao figurante 2
        boolean colisao2 = (posicao.x + velocidade.x < carroFigurante2.getX() + carroFigurante2.getImagem().getWidth())
                && (posicao.x + carroPersonagem.getImagem().getWidth() + velocidade.x > carroFigurante2.getX())
                && (posicao.y + velocidade.y < carroFigurante2.getY() + carroFigurante2.getImagem().getHeight())
                && (posicao.y + carroPersonagem.getImagem().getHeight() + velocidade.y > carroFigurante2.getY());

        if (colisao2) {
            velocidade.x = 0;
            velocidade.y = 0;
        }
        // colisao figurante 3
        boolean colisao3 = (posicao.x + velocidade.x < carroFigurante3.getX() + carroFigurante3.getImagem().getWidth())
                && (posicao.x + carroPersonagem.getImagem().getWidth() + velocidade.x > carroFigurante3.getX())
                && (posicao.y + velocidade.y < carroFigurante3.getY() + carroFigurante3.getImagem().getHeight())
                && (posicao.y + carroPersonagem.getImagem().getHeight() + velocidade.y > carroFigurante3.getY());

        if (colisao3) {
            velocidade.x = 0;
            velocidade.y = 0;
        }
        // colisao figurante 4
        boolean colisao4 = (posicao.x + velocidade.x < carroFigurante4.getX() + carroFigurante4.getImagem().getWidth())
                && (posicao.x + carroPersonagem.getImagem().getWidth() + velocidade.x > carroFigurante4.getX())
                && (posicao.y + velocidade.y < carroFigurante4.getY() + carroFigurante4.getImagem().getHeight())
                && (posicao.y + carroPersonagem.getImagem().getHeight() + velocidade.y > carroFigurante4.getY());

        if (colisao4) {
            velocidade.x = 0;
            velocidade.y = 0;
        }

        if (velocidade.y > 0) {
            System.out.println("");
            System.out.println("px" + posicao.x + " py" + posicao.y);
            System.out.println("vx" + velocidade.x + " vy" + velocidade.y);
            System.out.println("cx" + camera.position.x + " cy" + camera.position.y);
        }
            posicao.add(velocidade);
        // bater nas paredes
        if (posicao.x + carroPersonagem.getImagem().getWidth() < 0) {
            posicao.x = xIniciao;
        }
        if (posicao.x > mapaLargura) {
            posicao.x = 0 - carroPersonagem.getImagem().getWidth();
            posicao.x = xIniciao;
        }
        if (posicao.y > mapaAltura) {
            posicao.y = yIniciao;
        }
        if (posicao.y < 0) {
            posicao.y = yIniciao;
        }

        /*
        if (posicao.y  < yIniciao) {
            
        } else if (mapaAltura < yIniciao + posicao.y) {

        } else {
            camera.position.y = posicao.y;
        }
         */
        if (posicao.y > yIniciao + carroPersonagem.getImagem().getHeight() * 0.5f && 
            posicao.y  < mapaAltura - yIniciao+ carroPersonagem.getImagem().getHeight() * 0.5f) {
            camera.position.y = posicao.y;
        }

        if (posicao.x  > xIniciao + carroPersonagem.getImagem().getWidth() * 0.5f && 
            posicao.x < mapaLargura - yIniciao+ carroPersonagem.getImagem().getWidth() * 0.5f) {
            camera.position.x = posicao.x;

            //} else if (mapaLargura < xIniciao + posicao.x) {
            //} else {
            //  camera.position.x = posicao.x;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(carroPersonagem.getImagem(), posicao.x, posicao.y);

        batch.draw(carroFigurante1.getImagem(), carroFigurante1.getX(), carroFigurante1.getY());
        batch.draw(carroFigurante2.getImagem(), carroFigurante2.getX(), carroFigurante2.getY());
        batch.draw(carroFigurante3.getImagem(), carroFigurante3.getX(), carroFigurante3.getY());
        batch.draw(carroFigurante4.getImagem(), carroFigurante4.getX(), carroFigurante4.getY());
        fonte.setColor(Color.WHITE);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        batch2.dispose();
        imagemCarroFigurante.dispose();
        imagemCarroPrincipal.dispose();
        fonte.dispose();
    }

}
