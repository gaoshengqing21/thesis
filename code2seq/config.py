class Config:
    @staticmethod
    def get_default_config(args):
        config = Config(args)
        config.NUM_EPOCHS = 200
        config.SAVE_EVERY_EPOCHS = 1
        config.PATIENCE = 5
        config.BATCH_SIZE = 128
        config.TEST_BATCH_SIZE = 128
        config.READER_NUM_PARALLEL_BATCHES = 1
        config.SHUFFLE_BUFFER_SIZE = 10000
        config.CSV_BUFFER_SIZE = 100 * 1024 * 1024  # 100 MB
        config.MAX_CONTEXTS = 200
        config.SUBTOKENS_VOCAB_MAX_SIZE = 190000
        config.TARGET_VOCAB_MAX_SIZE = 999999
        config.EMBEDDINGS_SIZE = 128
        config.RNN_SIZE = 128 * 2  # Two LSTMs to embed paths, each of size 128
        config.DECODER_SIZE = 320
        config.NUM_DECODER_LAYERS = 1
        config.MAX_PATH_LENGTH = 8 + 1
        config.MAX_NAME_PARTS = 5
        config.MAX_TARGET_PARTS = 1
        config.EMBEDDINGS_DROPOUT_KEEP_PROB = 0.75
        config.RNN_DROPOUT_KEEP_PROB = 0.5
        config.BIRNN = True
        config.RANDOM_CONTEXTS = True
        config.BEAM_WIDTH = 0
        config.USE_MOMENTUM = True
        config.NO_BUG_STRING = "nobug"
        config.BUG_STRING = "bug"
        config.BUG_WEIGHT_FOR_LOSS = 1.0
        config.PICK_BEST_GPU = False
        return config

    @staticmethod
    def get_imbalanced_off_by_one_config(args):
        print("Setting imbalanced off-by-one config")
        config = Config(args)
        config.NUM_EPOCHS = 200
        config.SAVE_EVERY_EPOCHS = 1
        config.PATIENCE = 3
        config.BATCH_SIZE = 138
        config.TEST_BATCH_SIZE = 138
        config.READER_NUM_PARALLEL_BATCHES = 1
        config.SHUFFLE_BUFFER_SIZE = 10000
        config.CSV_BUFFER_SIZE = 100 * 1024 * 1024  # 100 MB
        config.MAX_CONTEXTS = 300
        config.SUBTOKENS_VOCAB_MAX_SIZE = 190000
        config.TARGET_VOCAB_MAX_SIZE = 999999
        config.EMBEDDINGS_SIZE = 110
        config.RNN_SIZE = 128 * 2  # Two LSTMs to embed paths, each of size 128
        config.DECODER_SIZE = 271
        config.NUM_DECODER_LAYERS = 2
        config.MAX_PATH_LENGTH = 10 + 1
        config.MAX_NAME_PARTS = 3
        config.MAX_TARGET_PARTS = 1
        config.EMBEDDINGS_DROPOUT_KEEP_PROB = 0.75
        config.RNN_DROPOUT_KEEP_PROB = 0.55
        config.BIRNN = True
        config.RANDOM_CONTEXTS = True
        config.BEAM_WIDTH = 0
        config.USE_MOMENTUM = True
        config.NO_BUG_STRING = "nobug"
        config.BUG_STRING = "bug"
        config.BUG_WEIGHT_FOR_LOSS = 1.0
        config.PICK_BEST_GPU = True
        return config

    @staticmethod
    def get_balanced_off_by_one_config(args):
        print("Setting balanced off-by-one config")
        config = Config(args)
        config.NUM_EPOCHS = 200
        config.SAVE_EVERY_EPOCHS = 1
        config.PATIENCE = 3
        config.BATCH_SIZE = 106
        config.TEST_BATCH_SIZE = 138
        config.READER_NUM_PARALLEL_BATCHES = 1
        config.SHUFFLE_BUFFER_SIZE = 10000
        config.CSV_BUFFER_SIZE = 100 * 1024 * 1024  # 100 MB
        config.MAX_CONTEXTS = 187
        config.SUBTOKENS_VOCAB_MAX_SIZE = 161513
        config.TARGET_VOCAB_MAX_SIZE = 999999
        config.EMBEDDINGS_SIZE = 114
        config.RNN_SIZE = 128 * 2  # Two LSTMs to embed paths, each of size 128
        config.DECODER_SIZE = 351
        config.NUM_DECODER_LAYERS = 2
        config.MAX_PATH_LENGTH = 21 + 1
        config.MAX_NAME_PARTS = 7
        config.MAX_TARGET_PARTS = 1
        config.EMBEDDINGS_DROPOUT_KEEP_PROB = 0.478
        config.RNN_DROPOUT_KEEP_PROB = 0.68
        config.BIRNN = True
        config.RANDOM_CONTEXTS = True
        config.BEAM_WIDTH = 0
        config.USE_MOMENTUM = True
        config.NO_BUG_STRING = "nobug"
        config.BUG_STRING = "bug"
        config.BUG_WEIGHT_FOR_LOSS = 1.0
        config.PICK_BEST_GPU = True
        return config

    def take_model_hyperparams_from(self, otherConfig):
        self.EMBEDDINGS_SIZE = otherConfig.EMBEDDINGS_SIZE
        self.RNN_SIZE = otherConfig.RNN_SIZE
        self.DECODER_SIZE = otherConfig.DECODER_SIZE
        self.NUM_DECODER_LAYERS = otherConfig.NUM_DECODER_LAYERS
        self.BIRNN = otherConfig.BIRNN
        if self.DATA_NUM_CONTEXTS <= 0:
            self.DATA_NUM_CONTEXTS = otherConfig.DATA_NUM_CONTEXTS

    def __init__(self, args):
        self.NUM_EPOCHS = 0
        self.SAVE_EVERY_EPOCHS = 0
        self.PATIENCE = 0
        self.BATCH_SIZE = 0
        self.TEST_BATCH_SIZE = 0
        self.READER_NUM_PARALLEL_BATCHES = 0
        self.SHUFFLE_BUFFER_SIZE = 0
        self.CSV_BUFFER_SIZE = None
        self.TRAIN_PATH = args.data_path
        self.TEST_PATH = args.test_path if args.test_path is not None else ''
        self.FIND_BUGS_PATH = args.find_data_path
        self.DATA_NUM_CONTEXTS = 0
        self.MAX_CONTEXTS = 0
        self.SUBTOKENS_VOCAB_MAX_SIZE = 0
        self.TARGET_VOCAB_MAX_SIZE = 0
        self.EMBEDDINGS_SIZE = 0
        self.RNN_SIZE = 0
        self.DECODER_SIZE = 0
        self.NUM_DECODER_LAYERS = 0
        self.SAVE_PATH = args.save_path_prefix
        self.LOAD_PATH = args.load_path
        self.MAX_PATH_LENGTH = 0
        self.MAX_NAME_PARTS = 0
        self.MAX_TARGET_PARTS = 0
        self.EMBEDDINGS_DROPOUT_KEEP_PROB = 0
        self.RNN_DROPOUT_KEEP_PROB = 0
        self.BIRNN = False
        self.RANDOM_CONTEXTS = True
        self.BEAM_WIDTH = 1
        self.USE_MOMENTUM = True
        self.RELEASE = args.release
        self.NO_BUG_STRING = ""
        self.BUG_STRING = ""
        self.BUG_WEIGHT_FOR_LOSS = 0.0
        self.PICK_BEST_GPU = False

    @staticmethod
    def get_debug_config(args):
        config = Config(args)
        config.NUM_EPOCHS = 3000
        config.SAVE_EVERY_EPOCHS = 100
        config.PATIENCE = 200
        config.BATCH_SIZE = 7
        config.TEST_BATCH_SIZE = 7
        config.READER_NUM_PARALLEL_BATCHES = 1
        config.SHUFFLE_BUFFER_SIZE = 10
        config.CSV_BUFFER_SIZE = None
        config.MAX_CONTEXTS = 5
        config.SUBTOKENS_VOCAB_MAX_SIZE = 190000
        config.TARGET_VOCAB_MAX_SIZE = 27000
        config.EMBEDDINGS_SIZE = 19
        config.RNN_SIZE = 10
        config.DECODER_SIZE = 11
        config.NUM_DECODER_LAYERS = 1
        config.MAX_PATH_LENGTH = 8 + 1
        config.MAX_NAME_PARTS = 5
        config.MAX_TARGET_PARTS = 1
        config.EMBEDDINGS_DROPOUT_KEEP_PROB = 1
        config.RNN_DROPOUT_KEEP_PROB = 1
        config.BIRNN = True
        config.RANDOM_CONTEXTS = True
        config.BEAM_WIDTH = 0
        config.USE_MOMENTUM = False
        config.NO_BUG_STRING = "nobug"
        config.BUG_STRING = "bug"
        config.BUG_WEIGHT_FOR_LOSS = 1.0
        config.PICK_BEST_GPU = False
        return config

    def update_values(self, updated_wandb_config):
        for key in updated_wandb_config.keys():
            setattr(self, key, updated_wandb_config[key])
        # Updating dependent variables in case they got updated
        self.RNN_SIZE = self.EMBEDDINGS_SIZE * 2
