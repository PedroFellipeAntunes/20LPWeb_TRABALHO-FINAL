const express = require('express');
const axios = require('axios');
const bodyParser = require('body-parser');
const app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.set('view engine', 'ejs');
app.use(express.static('public'));

const apiUrl = 'http://localhost:8080/api/clientes'; // URL da API Spring Boot
const ITEMS_PER_PAGE = 4; // Registros por página

// Rota de login (Agora a rota padrão)
app.get('/', (req, res) => {
    const error = req.query.error || ''; // Captura erro
    res.render('login', { error });
});

// Login
app.post('/', async (req, res) => {
    const { usuario, senha } = req.body;

    if (!usuario || !senha) {
        return res.redirect('/?error=Preencha todos os campos');
    }

    try {
        // Faz a requisição para o back-end Spring Boot
        const response = await axios.post('http://localhost:8080/api/auth/login', {
            usuario,
            senha
        });

        // Se o login for bem-sucedido, redireciona para a página de clientes
        if (response.status === 200) {
            return res.redirect('/clientes');
        }
    } catch (error) {
        // Se ocorrer um erro (login incorreto), redireciona com erro
        return res.redirect('/?error=Usuário ou senha incorretos');
    }
});

// Rota para listar clientes com pesquisa e paginação
app.get('/clientes', async (req, res) => {
    const search = req.query.search || ''; // Captura o parâmetro de pesquisa
    const page = parseInt(req.query.page) || 1; // Captura o número da página, com valor padrão de 1

    try {
        // Fazendo a requisição para a API Spring Boot, passando os parâmetros search e page
        const response = await axios.get(apiUrl, {
            params: {
                search, // Passa o termo de busca
                page, // Passa o número da página
                ITEMS_PER_PAGE
            }
        });

        // Obtém os clientes da resposta da API
        const clientes = response.data;
        
        // Para a paginação, agora a API é quem faz isso, então o totalPages já será retornado pela API
        const totalPages = response.headers['x-total-pages'] || 1; // Este valor pode ser retornado pela API, você pode ajustar no seu back-end

        res.render('index', { clientes, page, totalPages, search });
    } catch (error) {
        console.error(error);
        res.status(500).send('Erro ao buscar clientes');
    }
});

// Rota para exibir o formulário de cadastro
app.get('/novo', (req, res) => {
    res.render('cadastro');
});

// Rota para cadastrar um novo cliente
app.post('/novo', async (req, res) => {
    const { nome, nascimento, cpf, endereco, telefone, email } = req.body;
    try {
        await axios.post(apiUrl, { nome, nascimento, cpf, endereco, telefone, email });
        res.redirect('/clientes');
    } catch (error) {
        console.error(error);
        res.status(500).send('Erro ao cadastrar cliente');
    }
});

// Rota para exibir formulário de edição
app.get('/editar/:id', async (req, res) => {
    const { id } = req.params;
    try {
        const response = await axios.get(`${apiUrl}/${id}`);
        res.render('editar', { cliente: response.data });
    } catch (error) {
        console.error(error);
        res.status(500).send('Erro ao buscar cliente');
    }
});

// Rota para atualizar um cliente
app.post('/editar/:id', async (req, res) => {
    const { id } = req.params;
    const { nome, nascimento, cpf, endereco, telefone, email } = req.body;
    try {
        await axios.put(`${apiUrl}/${id}`, { nome, nascimento, cpf, endereco, telefone, email });
        res.redirect('/clientes');
    } catch (error) {
        console.error(error);
        res.status(500).send('Erro ao atualizar cliente');
    }
});

// Rota para excluir um cliente
app.post('/excluir/:id', async (req, res) => {
    const { id } = req.params;
    try {
        await axios.delete(`${apiUrl}/${id}`);
        res.redirect('/clientes');
    } catch (error) {
        console.log(error);
        res.status(500).send('Erro ao excluir cliente');
    }
});

app.listen(3000, () => {
    console.log('Servidor rodando na porta 3000');
});
